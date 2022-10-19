package com.is442project.cpa;

import com.is442project.cpa.common.email.Attachment;
import com.is442project.cpa.common.email.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;

@SpringBootTest
class CorporatePassApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private EmailService emailService;

	@Test
	public void sentHtmlEmail_givenValidEmail_shouldSendEmail() {
		emailService.sendHtmlMessage("joshua.zhangzy@gmail.com", "Project IS442", "HELLO WORLD! <br> <br> <h2>Let's Party!</h2>");
	}

	@Test
	public void sentHtmlEmailWithAttachments_givenOneAttachhment_shouldSendEmail(){
		//arrange
		File file = new File("AuthorisationLetterZoo.pdf");
		Attachment attachment = new Attachment("Zoo Authorization Letter", file);

		//act
		emailService.sendHtmlMessageWithAttachments("joshua.zhangzy@gmail.com", "Project IS442",
				"HELLO WORLD! <br> <br> <h2>Let's Party!</h2>", List.of(attachment));
	}

}
