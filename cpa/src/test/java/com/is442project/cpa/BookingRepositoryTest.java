package com.is442project.cpa;

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

import com.is442project.cpa.account.model.UserAccountRepository;
import com.is442project.cpa.account.seeder.UserSeeder;
import com.is442project.cpa.booking.model.Booking;
import com.is442project.cpa.booking.model.BookingRepository;
import com.is442project.cpa.booking.model.CorporatePassRepository;
import com.is442project.cpa.booking.model.MembershipRepository;
import com.is442project.cpa.booking.seeder.BookingSeeder;
import com.is442project.cpa.booking.seeder.CorporatePassSeeder;
import com.is442project.cpa.booking.seeder.MembershipSeeder;

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

        List<Booking> bookingList = bookingRepository.findAll();

        //action
        bookingList.forEach(booking -> System.out.println(booking.getBookingDate().toString()));

        //assert
        assertThat(bookingList).extracting(booking -> booking.getBorrowDate().isAfter(LocalDate.now())).contains(true);
    }
}
