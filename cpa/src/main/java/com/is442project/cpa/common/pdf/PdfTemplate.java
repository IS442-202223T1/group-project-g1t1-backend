package com.is442project.cpa.common.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;

public interface PdfTemplate {

    String LETTER_HEAD_URL = "";

    PDDocument getPdfDocument();
}
