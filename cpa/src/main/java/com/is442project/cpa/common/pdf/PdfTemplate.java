package com.is442project.cpa.common.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;

public interface PdfTemplate {

    String LETTER_HEAD_URL = "src/main/resources/images/LetterHead.png";

    PDDocument getPdfDocument();
}
