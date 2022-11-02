package com.is442project.cpa.booking;

import com.is442project.cpa.common.template.AttachmentTemplate;
import com.is442project.cpa.common.template.EmailTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("dev")
public class MembershipSeeder {

    public final MembershipRepository membershipRepository;

    public MembershipSeeder(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;

        insertTestData();
    }

    public void insertTestData() {
        EmailTemplate emailTemplate = new EmailTemplate();
        AttachmentTemplate attachmentTemplate = new AttachmentTemplate();
        emailTemplate.setTemplateContent("test");
        attachmentTemplate.setTemplateContent("test");
        Membership membership = new Membership("Jalan Besar Stadium", emailTemplate, attachmentTemplate);
        Membership membership2 = new Membership("Bedok Stadium", emailTemplate, attachmentTemplate);
        Membership membership3 = new Membership("Temasek Junior College", emailTemplate, attachmentTemplate);

        membershipRepository.saveAllAndFlush(Arrays.asList(membership, membership2, membership3));

        System.out.println("======TEST MEMBERSHIP INSERTED======");
    }
}