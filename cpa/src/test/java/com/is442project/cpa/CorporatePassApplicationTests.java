package com.is442project.cpa;

import com.is442project.cpa.account.AccountService;
import com.is442project.cpa.booking.Booking;
import com.is442project.cpa.booking.BookingRepository;
import com.is442project.cpa.booking.Membership;
import com.is442project.cpa.booking.MembershipRepository;
import com.is442project.cpa.common.email.Attachment;
import com.is442project.cpa.common.email.EmailService;
import com.is442project.cpa.common.pdf.AuthorizationLetter;
import com.is442project.cpa.common.pdf.ElectronicPass;
import com.is442project.cpa.common.pdf.PdfFactory;
import com.is442project.cpa.common.template.AuthorizationLetterTemplate;
import com.is442project.cpa.common.template.ElectronicPassTemplate;
import com.is442project.cpa.common.template.EmailTemplate;
import com.is442project.cpa.common.template.TemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class CorporatePassApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private EmailService emailService;

	@Autowired
	MembershipRepository membershipRepository;

	@Autowired
	AccountService accountService;

	@Autowired
	BookingRepository bookingRepository;

	@Test
	public void sentHtmlEmail_givenValidEmail_shouldSendEmail() {
		emailService.sendHtmlMessage("is442.2022group1@gmail.com", "Project IS442", "HELLO WORLD! <br> <br> <h2>Let's Party!</h2>");
	}

	@Test
	public void sentHtmlEmailWithAttachments_givenOneAttachhment_shouldSendEmail(){
		//arrange
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Date: <current_date>%n%n"));

		Booking booking = bookingRepository.findById(5).get();

		AuthorizationLetterTemplate attachmentTemplate = new AuthorizationLetterTemplate(sb.toString(), Arrays.asList(booking));
		AuthorizationLetter authorizationLetter = new AuthorizationLetter(attachmentTemplate);
		PdfFactory pdfFactory = new PdfFactory(authorizationLetter);
		try {
			Attachment attachment = new Attachment("Zoo Authorization Letter", pdfFactory.generatePdfFile());

		//act
		emailService.sendHtmlMessageWithAttachments("is442.2022group1@gmail.com", "Project IS442",
				"HELLO WORLD! <br> <br> <h2>Let's Party!</h2>", List.of(attachment));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	@Test
	public void GenerateEmailContent_usingTemplateEngine_shouldGenerateContent() {
		//arrange
		Membership sampleMemberShip = membershipRepository.findById(Long.valueOf(1)).get();


		Booking booking = bookingRepository.findById(5).get();

		EmailTemplate emailTemplate = new EmailTemplate(sampleMemberShip.getAttachmentTemplate().getTemplateContent(), Arrays.asList(booking));


		TemplateEngine templateEngine = new TemplateEngine(emailTemplate);

		//act
		System.out.println(templateEngine.getContent());
	}

	@Test
	public void GenerateLetterPDF() {
		//arrange
		Membership sampleMemberShip = membershipRepository.findById(4L).get();


		Booking booking = bookingRepository.findById(2).get();

		ElectronicPassTemplate electronicPassTemplate = new ElectronicPassTemplate(sampleMemberShip.getAttachmentTemplate().getTemplateContent(), booking);
		ElectronicPass ePass = new ElectronicPass(electronicPassTemplate, booking);

		PdfFactory pdfFactory = new PdfFactory(ePass);

		ByteArrayDataSource bads = pdfFactory.generatePdfFile();

		//act
		try {
			Path path = Paths.get("letter.pdf");
			Files.write(path, bads.getInputStream().readAllBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
