package com.is442project.cpa.common.email;
import javax.mail.util.ByteArrayDataSource;

public record Attachment(String fileName, ByteArrayDataSource file) {
}
