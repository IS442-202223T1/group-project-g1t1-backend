package com.is442project.cpa;

import com.is442project.cpa.account.AccountService;
import com.is442project.cpa.booking.Booking;
import com.is442project.cpa.booking.Membership;
import com.is442project.cpa.booking.MembershipRepository;
import com.is442project.cpa.common.email.Attachment;
import com.is442project.cpa.common.email.EmailService;
import com.is442project.cpa.common.pdf.AuthorizationLetter;
import com.is442project.cpa.common.pdf.PdfFactory;
import com.is442project.cpa.common.template.AttachmentTemplate;
import com.is442project.cpa.common.template.EmailTemplate;
import com.is442project.cpa.common.template.TemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
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

	@Test
	public void sentHtmlEmail_givenValidEmail_shouldSendEmail() {
		emailService.sendHtmlMessage("is442.2022group1@gmail.com", "Project IS442", "HELLO WORLD! <br> <br> <h2>Let's Party!</h2>");
	}

	@Test
	public void sentHtmlEmailWithAttachments_givenOneAttachhment_shouldSendEmail(){
		//arrange
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Date: <current_date>%n%n"));

		AttachmentTemplate attachmentTemplate = new AttachmentTemplate(sb.toString());
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
		Membership sampleMemberShip = membershipRepository.findById("Jalan Besar Stadium").get();

		EmailTemplate emailTemplate = new EmailTemplate(sampleMemberShip.getAttachmentTemplate().getTemplateContent());

		Booking booking = new Booking(LocalDate.now(), accountService.readUserByEmail("testAdmin@gmail.com"));

		emailTemplate.setBooking(booking);

		TemplateEngine templateEngine = new TemplateEngine(emailTemplate);

		//act
		System.out.println(templateEngine.getContent());
	}

}
