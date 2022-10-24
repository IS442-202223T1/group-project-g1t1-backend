package com.is442project.cpa.booking;

import com.is442project.cpa.common.template.AttachmentTemplate;
import com.is442project.cpa.common.template.EmailTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("dev")
public class BookingSeeder {

    private final MembershipRepository membershipRepository;

    public BookingSeeder(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
        insertTestMembership();
    }

    private void insertTestMembership() {

        StringBuilder templateContent = new StringBuilder();
        templateContent.append("Dear <borrower_name>,\n\n");
        templateContent.append("We are pleased to inform that your booking to <attraction_name> is confirmed as follows:");

        Membership testMembership01 = new Membership(new EmailTemplate(templateContent.toString()), new AttachmentTemplate(templateContent.toString()));

        membershipRepository.saveAllAndFlush(Arrays.asList(testMembership01));
    }
}
