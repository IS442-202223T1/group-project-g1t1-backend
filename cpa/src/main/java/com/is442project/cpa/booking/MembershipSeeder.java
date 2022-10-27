package com.is442project.cpa.booking;

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
        Membership membership = new Membership("Jalan Besar Stadium");
        Membership membership2 = new Membership("Bedok Stadium");
        Membership membership3 = new Membership("Temasek Junior College");

        membershipRepository.saveAllAndFlush(Arrays.asList(membership, membership2, membership3));

        System.out.println("======TEST USER ACCOUNT INSERTED======");
    }
}

