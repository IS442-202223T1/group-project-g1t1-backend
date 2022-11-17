package com.is442project.cpa.account.seeder;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

import com.is442project.cpa.account.model.Administrator;
import com.is442project.cpa.account.model.Borrower;
import com.is442project.cpa.account.model.GeneralOfficePersonnel;
import com.is442project.cpa.account.model.UserAccount;
import com.is442project.cpa.account.model.UserAccountRepository;

@Configuration
@Profile("dev")
public class UserSeeder {

    public final UserAccountRepository userAccountRepository;

    public UserSeeder(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
        insertTestData();
    }

    public void insertTestData() {
        //ADMIN
        UserAccount admin = new UserAccount("ezra.chooi@gmail.com", "Ezra", "ezra", "81525893", Arrays.asList(new Administrator(), new Borrower(), new GeneralOfficePersonnel()));

        //GOP
        UserAccount user01 = new UserAccount("david@sportsschool.edu.sg", "David Tan", "davidtan", "87451258", Arrays.asList(new GeneralOfficePersonnel()));

        //Existing Borrower
        UserAccount user02 = new UserAccount("joshua.zhangzy@gmail.com", "Joshua", "Joshua", "95268745", Arrays.asList(new Borrower()));
        UserAccount user03 = new UserAccount("mary@nysi.org.sg", "Mary Tan", "Mary", "94568217", Arrays.asList(new Borrower()));
        UserAccount user04 = new UserAccount("Carol@nysi.org.sg", "Carol Lee", "Carol", "94353255", Arrays.asList(new Borrower()));
        UserAccount user05 = new UserAccount("Hugo@nysi.org.sg", "Hugo Ooi", "Hugo", "96528216", Arrays.asList(new Borrower()));
        UserAccount user06 = new UserAccount("Kelvin@sportsschool.edu.sg", "Kelvin Lim", "Kelvin", "94598512", Arrays.asList(new Borrower()));
        UserAccount user07 = new UserAccount("Kennedy@sportsschool.edu.sg", "Kennedy Teo", "Kennedy", "98546317", Arrays.asList(new Borrower()));
        UserAccount user08 = new UserAccount("Emmy@sportsschool.edu.sg", "Emmy Goh", "Emmy", "94514552", Arrays.asList(new Borrower()));
        UserAccount user09 = new UserAccount("Elliot@sportsschool.edu.sg", "Elliot Tan", "Elliot", "91269362", Arrays.asList(new Borrower()));
        UserAccount user10 = new UserAccount("Willie@sportsschool.edu.sg", "Willie Sim", "Willie", "84565537", Arrays.asList(new Borrower()));

        userAccountRepository.saveAllAndFlush(Arrays.asList(admin, user01, user02, user03,user04, user05,user06,user07,user08,user09,user10));

        System.out.println("======TEST USER ACCOUNTS INSERTED======");
    }
}
