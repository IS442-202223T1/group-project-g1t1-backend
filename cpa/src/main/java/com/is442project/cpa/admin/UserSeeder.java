package com.is442project.cpa.admin;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

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
        UserAccount admin = new UserAccount("testAdmin@gmail.com", "TestAdmin", "testadmin", Arrays.asList(new Administrator()));

        UserAccount user01 = new UserAccount("david@sportsschool.edu.sg", "David Tan");
        UserAccount user02 = new UserAccount("mary@nysi.org.sg", "Mary Lim");

        userAccountRepository.saveAllAndFlush(Arrays.asList(admin, user01, user02));

        System.out.println("======TEST USER ACCOUNT INSERTED======");
    }
}
