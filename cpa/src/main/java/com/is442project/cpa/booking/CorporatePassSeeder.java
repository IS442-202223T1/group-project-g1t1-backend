package com.is442project.cpa.booking;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.is442project.cpa.common.template.*;

import java.util.Arrays;

@Configuration
@Profile("dev")
public class CorporatePassSeeder {

    public final CorporatePassRepository corporatePassRepository;
    public final MembershipRepository membershipRepository;

    public CorporatePassSeeder(CorporatePassRepository corporatePassRepository, MembershipRepository membershipRepository) {
        this.corporatePassRepository = corporatePassRepository;
        this.membershipRepository = membershipRepository;
        insertTestData();
    }

    public void insertTestData() {
        EmailTemplate emailTemplate = new EmailTemplate();
        AttachmentTemplate attachmentTemplate = new AttachmentTemplate();
        Membership membership = new Membership("Jalan Besar Stadium", emailTemplate, attachmentTemplate);
        membershipRepository.saveAllAndFlush(Arrays.asList(membership));
        CorporatePass pass1 = new CorporatePass(membership, "A1", "available", 4);
        CorporatePass pass2 = new CorporatePass(membership, "B2", "collected", 4);
        CorporatePass pass3 = new CorporatePass(membership, "C3", "returned", 4);
        CorporatePass pass4 = new CorporatePass(membership, "D4", "losted", 4);

        corporatePassRepository.saveAllAndFlush(Arrays.asList(pass1, pass2, pass3, pass4));

        System.out.println("======TEST USER ACCOUNT INSERTED======");
    }
}
