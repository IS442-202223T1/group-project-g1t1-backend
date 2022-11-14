package com.is442project.cpa;


import com.is442project.cpa.account.UserAccountRepository;
import com.is442project.cpa.account.UserSeeder;
import com.is442project.cpa.booking.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingRepositoryTest {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    UserAccountRepository userAccountRepository;

    @Autowired
    MembershipRepository membershipRepository;

    @Autowired
    CorporatePassRepository corporatePassRepository;


    @BeforeAll
    public void setup() {
        UserSeeder userSeeder = new UserSeeder(userAccountRepository);
        userSeeder.insertTestData();

        MembershipSeeder membershipSeeder = new MembershipSeeder(membershipRepository);
        membershipSeeder.insertTestData();

        CorporatePassSeeder corporatePassSeeder = new CorporatePassSeeder(corporatePassRepository, membershipRepository);
        corporatePassSeeder.insertTestData();

        BookingSeeder bookingSeeder = new BookingSeeder(corporatePassRepository, bookingRepository, userAccountRepository);
    }

    @Test
    public void seedBooking() {

        //arrange
//        UserAccount user = userAccountRepository.findById("testAdmin@gmail.com").orElse(null);

        List<Booking> bookingList = bookingRepository.findByBorrowDateAfter(LocalDate.now());

        //action

        //assert
        assertThat(bookingList).extracting(booking -> booking.getBorrowDate().isAfter(LocalDate.now())).contains(true);
    }
}
