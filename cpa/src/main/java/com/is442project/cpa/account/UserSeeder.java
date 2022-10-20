package com.is442project.cpa.account;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("dev")
public class UserSeeder {

    public final UserAccountRepository userAccountRepository;

    public UserSeeder(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;

        insertTestData();
    }

    public void insertTestData() {
        UserAccount admin = new UserAccount("testAdmin@gmail.com", "TestAdmin", "testadmin", null, Arrays.asList(new Administrator(), new Borrower()));

        UserAccount user01 = new UserAccount("david@sportsschool.edu.sg", "David Tan", "davidtan", null, Arrays.asList(new Borrower()));
        UserAccount user02 = new UserAccount("mary@nysi.org.sg", "Mary Lim", "marylim", null, Arrays.asList(new Borrower()));

        userAccountRepository.saveAllAndFlush(Arrays.asList(admin, user01, user02));

        System.out.println("======TEST USER ACCOUNT INSERTED======");
    }
}
