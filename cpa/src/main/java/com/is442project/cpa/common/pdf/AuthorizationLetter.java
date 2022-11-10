package com.is442project.cpa.common.pdf;

import com.is442project.cpa.common.template.TemplateEngine;
import com.is442project.cpa.common.template.TemplateResources;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class AuthorizationLetter implements PdfTemplate{

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
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.newLineAtOffset(25, 500);

            contentStream.showText(templateEngine.getContent().replaceAll("\n", "").replaceAll("\r",""));
//            contentStream.showText("some sample content");
            contentStream.endText();


            contentStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return document;
    }
}
