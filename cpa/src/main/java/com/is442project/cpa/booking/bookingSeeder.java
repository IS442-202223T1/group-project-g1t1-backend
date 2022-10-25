package com.is442project.cpa.booking;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.is442project.cpa.account.AccountService;
import com.is442project.cpa.account.UserAccount;

import java.time.LocalDate;
import java.util.Arrays;

@Configuration
@Profile("dev")
public class bookingSeeder {

    public final BookingRepository bookingRepository;
    private final AccountService accountService;

    public bookingSeeder(BookingRepository bookingRepository, AccountService accountService) {
        this.bookingRepository = bookingRepository;
        this.accountService = accountService;

        insertTestData();
    }

    public void insertTestData() {
        UserAccount borrower1 = accountService.readUserByEmail("mary@nysi.org.sg");
        UserAccount borrower2 = accountService.readUserByEmail("david@sportsschool.edu.sg");

        Booking booking1 = new Booking(LocalDate.parse("2022-10-30"), borrower1);
        Booking booking2 = new Booking(LocalDate.parse("2022-10-05"), borrower1);
        Booking booking3 = new Booking(LocalDate.parse("2021-10-30"), borrower1);

        Booking booking4 = new Booking(LocalDate.parse("2022-09-26"), borrower2);
        Booking booking5 = new Booking(LocalDate.parse("2022-09-05"), borrower2);
        Booking booking6 = new Booking(LocalDate.parse("2021-08-30"), borrower2);

        bookingRepository.saveAllAndFlush(Arrays.asList(booking1,booking2,booking3,booking4,booking5,booking6));

        System.out.println("======TEST BOOKINGS INSERTED======");
    }
}
