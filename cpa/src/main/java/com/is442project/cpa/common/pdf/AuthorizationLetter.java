package com.is442project.cpa.common.pdf;

import com.is442project.cpa.common.template.TemplateEngine;
import com.is442project.cpa.common.template.TemplateResources;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;

public class AuthorizationLetter implements PdfTemplate {

    private final PDDocument document = new PDDocument();

    private final TemplateResources templateResources;

    public AuthorizationLetter(TemplateResources templateResources) {
        this.templateResources = templateResources;
    }

    @Override
    public PDDocument getPdfDocument() {

        TemplateEngine templateEngine = new TemplateEngine(templateResources);

        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            PDImageXObject letterHeadImage = PDImageXObject.createFromFile(PdfTemplate.LETTER_HEAD_URL, document);

            contentStream.drawImage(letterHeadImage, 0, page.getTrimBox().getHeight() - 120, page.getTrimBox().getWidth(), 110);

            //PDFBox library showtext method cannot have String containing new line or carriage symbol
            //Lastly to split all html <br> tags to manually print newline in pdf.
            String[] pdfContents = templateEngine.getContent().replaceAll("\n", "")
                    .replaceAll("\r", "").split("<br>");

            contentStream.beginText();
            contentStream.setLeading(12.0);
            contentStream.newLineAtOffset(70, page.getTrimBox().getHeight() - 160);
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);

            //WordUtils.wrap not working so implement manual wrapping.
            int wrapLength = 100;
            for (String pdfContent : pdfContents) {
                if (!pdfContent.isEmpty()) {
                    String[] subContent = pdfContent.split(" ");

                    int charQuota = wrapLength;
                    String perLine = "";
                    for (String s : subContent) {
                        charQuota -= s.length() + 1;
                        if (charQuota > 0) {
                            perLine += s + " ";
                        } else {
                            contentStream.showText(perLine.substring(0, perLine.length() - 1));
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

            contentStream.endText();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return document;
    }
    
}
