package com.is442project.cpa.common.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Value;

public interface PdfTemplate {

    String LETTER_HEAD_URL = "src/main/resources/images/LetterHead.png";
    String CORPORATE_MEMBER_NAME = "Singapore Sports School";

    PDDocument getPdfDocument();
}
