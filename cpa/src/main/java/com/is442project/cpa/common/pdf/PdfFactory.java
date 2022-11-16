package com.is442project.cpa.common.pdf;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.mail.util.ByteArrayDataSource;

public class PdfFactory {

    private final PDDocument pdDocument;

    public PdfFactory(PdfTemplate pdfTemplate)  {
        pdDocument = pdfTemplate.getPdfDocument();
    }

    public ByteArrayDataSource generatePdfFile() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            pdDocument.save(os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            return new ByteArrayDataSource(is.readAllBytes(), "application/pdf");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
