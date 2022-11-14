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
        UserAccount admin = new UserAccount("testAdmin@gmail.com", "TestAdmin", "testadmin", null, Arrays.asList(new Administrator(), new Borrower(), new GeneralOfficePersonnel()));

        UserAccount user01 = new UserAccount("david@sportsschool.edu.sg", "David Tan", "davidtan", null, Arrays.asList(new Borrower()));
        UserAccount user02 = new UserAccount("mary@nysi.org.sg", "Mary Lim", "marylim", null, Arrays.asList(new Borrower()));
        UserAccount user03 = new UserAccount("joshua.zhangzy@gmail.com", "Joshua", "Joshua", null, Arrays.asList(new Borrower()));

        userAccountRepository.saveAllAndFlush(Arrays.asList(admin, user01, user02, user03));

        System.out.println("======TEST USER ACCOUNT INSERTED======");
    }
}
