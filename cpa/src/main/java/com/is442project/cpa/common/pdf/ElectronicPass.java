package com.is442project.cpa.common.pdf;

import com.is442project.cpa.booking.model.Booking;
import com.is442project.cpa.common.template.TemplateEngine;
import com.is442project.cpa.common.template.TemplateResources;
import com.is442project.cpa.config.model.GlobalConfig;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ElectronicPass implements PdfTemplate {

    private final PDDocument document = new PDDocument();

    private final TemplateResources templateResources;

    private final Booking booking;

    private final int passSeq;

    private final GlobalConfig globalConfig;

    public ElectronicPass(GlobalConfig globalConfig, TemplateResources templateResources, Booking booking, int passSeq) {
        this.globalConfig = globalConfig;
        this.templateResources = templateResources;
        this.booking = booking;
        this.passSeq = passSeq;
    }

    @Override
    public PDDocument getPdfDocument() {

        TemplateEngine templateEngine = new TemplateEngine(templateResources);

        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            //add letter head
            PDImageXObject letterHeadImage = PDImageXObject.createFromFile(globalConfig.getLetterHeadUrl(), document);

            contentStream.drawImage(letterHeadImage, 0, page.getTrimBox().getHeight() - 120,
                    letterHeadImage.getWidth(), letterHeadImage.getHeight());

            //generate barcode
            int barcodeWidth = 200;
            addBarcode(document, page, booking.getCorporatePass().getPassID(),
                    page.getTrimBox().getWidth()-300, page.getTrimBox().getHeight()-190,
                    barcodeWidth, 110);

            String [] barcodeDescription = new String[3];
            barcodeDescription[0] = booking.getCorporatePass().getMembership().getMembershipGrade() == null ? "Default" :booking.getCorporatePass().getMembership().getMembershipGrade() ;
            barcodeDescription[1] = String.format("(Booking #%d)", passSeq);
            barcodeDescription[2] = booking.getCorporatePass().getExpiryDate() == null ? "Not Defined" : booking.getCorporatePass().getExpiryDate().format(DateTimeFormatter.ofPattern(globalConfig.getDateFormat()));

            PDFont font = PDType1Font.TIMES_ROMAN;
            int fontSize = 12;
            for (int i = 0; i < barcodeDescription.length ; i++) {
                float xLeftWidthBalanceSpace = (barcodeWidth - font.getStringWidth(barcodeDescription[i])/1000 *fontSize)/2;
                float yFontHeight= font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
                contentStream.beginText();
                contentStream.newLineAtOffset(page.getTrimBox().getWidth()-300+xLeftWidthBalanceSpace,
                        (page.getTrimBox().getHeight()-190-15) - yFontHeight * (i+1));
                contentStream.setFont(font, fontSize);
                contentStream.showText(barcodeDescription[i]);
                contentStream.endText();
            }

            //add attraction logo
            float scale = 0.4f;
            PDImageXObject attractionLogo = PDImageXObject.createFromFile(booking.getCorporatePass().getMembership().getLogoUrl(), document);

            contentStream.drawImage(attractionLogo, 75, page.getTrimBox().getHeight() - 250,
                    attractionLogo.getWidth()*scale, attractionLogo.getHeight()*scale);

            //PDFBox library showtext method cannot have String containing new line or carriage symbol
            //Lastly to split all html <br> tags to manually print newline in pdf.
            String[] pdfContents = templateEngine.getContent().replaceAll("\n", "")
                    .replaceAll("\r", "").split("<br>");

            List<String> pdfContentsList = new ArrayList<>();

            //process html <li> elements
            for (String sentence : pdfContents) {
                sentence = sentence.replaceAll("</li>", "").replaceAll("<ul>","").replaceAll("</ul>", "");

                if(sentence.contains("<li>")) {
                    String[] splitSentence = sentence.split("<li>");

                    for (int i = 1; i < splitSentence.length; i++) {
                            pdfContentsList.add("- " + splitSentence[i]);
                            pdfContentsList.add("");
                    }
                } else {
                    pdfContentsList.add(sentence);
                }
            }

            contentStream.beginText();
            contentStream.setLeading(12.0);
            contentStream.newLineAtOffset(70, page.getTrimBox().getHeight() - 160);
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);

            //WordUtils.wrap not working so implement manual wrapping.
            int wrapLength = 100;
            for (String pdfContent : pdfContentsList) {
                if (!pdfContent.isEmpty()) {
                    String[] subContent = pdfContent.split(" ");

                    int charQuota = wrapLength;
                    String perLine = "";
                    for (String s : subContent) {
                        charQuota -= s.length() + 1; //plus 1 to account for the space added at the end of the word
                        if (charQuota > 0) {
                            perLine += s + " ";
                        } else {
                            contentStream.showText(perLine.substring(0, perLine.length() - 1)); //minus 1 to account for the additional space at the last word
                            contentStream.newLine();
                            charQuota = wrapLength;
                            perLine = s + " ";
                            charQuota -= s.length()+1;
                        }
                    }
                    contentStream.showText(perLine.substring(0, perLine.length() - 1));
                    contentStream.newLine();

                } else {
                    contentStream.showText(pdfContent);
                    contentStream.newLine();
                }
            }
            contentStream.showText(globalConfig.getCorporateMemberName().toUpperCase());
            contentStream.newLine();

            contentStream.endText();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return document;
    }

    private void addBarcode(PDDocument document, PDPage page, String text, float x, float y, int width, int height) {
        try {
            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);

            int dpi = 300;
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
            Code39Bean code39Bean = new Code39Bean();
            code39Bean.generateBarcode(canvas, text.trim());
            canvas.finish();
            BufferedImage bImage = canvas.getBufferedImage();

            float scale = 0.2f;
            PDImageXObject image = JPEGFactory.createFromImage(document, bImage);
            contentStream.drawImage(image, x, y, width, height*scale);

            //draw border around the barcode image
            contentStream.setStrokingColor(Color.black);
            contentStream.addRect(x-10, y-8, width+20, height*scale+16);
            contentStream.closeAndStroke();

            contentStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
