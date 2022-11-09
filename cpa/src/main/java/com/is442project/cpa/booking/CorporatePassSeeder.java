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
        Membership membership1 =  membershipRepository.findById(Long.valueOf(1)).get();
        Membership membership2 =  membershipRepository.findById(Long.valueOf(2)).get();
        Membership membership3 =  membershipRepository.findById(Long.valueOf(3)).get();
        Membership membership4 =  membershipRepository.findById(Long.valueOf(4)).get();

        CorporatePass pass1 = new CorporatePass(membership1, "CARD0001", Status.AVAILABLE, 4);
        CorporatePass pass2 = new CorporatePass(membership1, "CARD0002", Status.LOANED, 4);
        CorporatePass pass3 = new CorporatePass(membership1, "CARD0003", Status.AVAILABLE, 4);
        CorporatePass pass4 = new CorporatePass(membership1, "CARD0004", Status.LOST, 4);
        CorporatePass pass5 = new CorporatePass(membership2, "CARD0005", Status.AVAILABLE, 4);
        CorporatePass pass6 = new CorporatePass(membership2, "CARD0006", Status.LOST, 4);
        CorporatePass pass7 = new CorporatePass(membership2, "CARD0007", Status.LOANED, 4);
        CorporatePass pass8 = new CorporatePass(membership3, "CARD0008", Status.LOANED, 2);
        CorporatePass pass9 = new CorporatePass(membership4, "CARD0009", Status.LOANED, 2);

        corporatePassRepository.saveAllAndFlush(Arrays.asList(pass1, pass2, pass3, pass4, pass5, pass6, pass7, pass8, pass9));

        System.out.println("======TEST CORPORATE PASS INSERTED======");
    }
}
