package com.is442project.cpa.booking.seeder;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

import com.is442project.cpa.booking.model.CorporatePass;
import com.is442project.cpa.booking.model.CorporatePassRepository;
import com.is442project.cpa.booking.model.Membership;
import com.is442project.cpa.booking.model.MembershipRepository;
import com.is442project.cpa.booking.model.CorporatePass.Status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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
        CorporatePass pass20 = new CorporatePass(membership4, "22401046113600009088", Status.LOANED, 2);
        CorporatePass pass21 = new CorporatePass(membership4, "22401046113600009089", Status.LOANED, 2);
        CorporatePass pass22 = new CorporatePass(membership4, "22401046113600009090", Status.AVAILABLE, 2);
        CorporatePass pass23 = new CorporatePass(membership4, "22401046113600009091", Status.AVAILABLE, 2);
        CorporatePass pass24 = new CorporatePass(membership4, "22401046113600009092", Status.AVAILABLE, 2);

        List<CorporatePass> corporatePasses = Arrays.asList(pass1, pass2, pass3, pass4, pass5, pass6, pass7, pass8, pass20, pass21, pass22, pass23, pass24);

        corporatePasses.stream().forEach(corporatePass -> corporatePass.setExpiryDate(LocalDate.of(2023,03, 31)));
        corporatePassRepository.saveAllAndFlush(corporatePasses);

        System.out.println("======TEST CORPORATE PASSES INSERTED======");

    }
}
