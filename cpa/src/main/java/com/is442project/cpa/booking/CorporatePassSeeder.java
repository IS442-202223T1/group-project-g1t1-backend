package com.is442project.cpa.booking;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import com.is442project.cpa.booking.CorporatePass.Status;

import java.util.Arrays;


@Configuration
@Profile("dev")
@DependsOn("membershipSeeder")
public class CorporatePassSeeder {

    public final CorporatePassRepository corporatePassRepository;
    public final MembershipRepository membershipRepository;

    public CorporatePassSeeder(CorporatePassRepository corporatePassRepository, MembershipRepository membershipRepository) {
        this.corporatePassRepository = corporatePassRepository;
        this.membershipRepository = membershipRepository;
        insertTestData();
    }

    public void insertTestData() {
        CorporatePass pass1 = new CorporatePass("Mandai Wildlife Reserve", "CARD0001", Status.AVAILABLE, 4, "Physical");
        CorporatePass pass2 = new CorporatePass("Mandai Wildlife Reserve", "CARD0002", Status.LOANED, 4, "Physical");
        CorporatePass pass3 = new CorporatePass("Mandai Wildlife Reserve", "CARD0003", Status.AVAILABLE, 4, "Digital");
        CorporatePass pass4 = new CorporatePass("Mandai Wildlife Reserve", "CARD0004", Status.LOST, 4, "Physical");

        corporatePassRepository.saveAllAndFlush(Arrays.asList(pass1, pass2, pass3, pass4));

        System.out.println("======TEST CORPORATE PASS INSERTED======");
    }
}
