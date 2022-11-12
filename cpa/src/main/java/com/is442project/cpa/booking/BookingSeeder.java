package com.is442project.cpa.booking;

import com.is442project.cpa.account.AccountService;
import com.is442project.cpa.account.UserAccount;
import com.is442project.cpa.booking.Booking.BookingStatus;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.Arrays;

@Configuration
@Profile("dev")
@DependsOn({"userSeeder", "membershipSeeder", "corporatePassSeeder"})
public class BookingSeeder {

    private final MembershipRepository membershipRepository;

    private final CorporatePassRepository corporatePassRepository;

    private BookingRepository bookingRepository;

    private final AccountService accountService;

    public BookingSeeder(MembershipRepository membershipRepository, CorporatePassRepository corporatePassRepository, BookingRepository bookingRepository, AccountService accountService) {
        this.membershipRepository = membershipRepository;
        this.corporatePassRepository = corporatePassRepository;
        this.bookingRepository = bookingRepository;
        this.accountService = accountService;
        insertTestBooking();
    }

    private void insertTestBooking(){
        UserAccount borrower1 = accountService.readUserByEmail("joshua.zhangzy@gmail.com");
        CorporatePass corporatePass1 = corporatePassRepository.findById(Long.parseLong("1")).get();

        UserAccount borrower2 = accountService.readUserByEmail("mary@nysi.org.sg");
        CorporatePass corporatePass2 = corporatePassRepository.findById(Long.parseLong("2")).get();

        CorporatePass corporatePass5 = corporatePassRepository.findById(Long.parseLong("5")).get();
        CorporatePass corporatePass6 = corporatePassRepository.findById(Long.parseLong("6")).get();
        
        Booking booking1 = new Booking(LocalDate.of(2022, 11, 16), borrower1, corporatePass1);
        Booking booking2 = new Booking(LocalDate.of(2022, 11, 16), borrower1, corporatePass2);
        Booking booking3 = new Booking(LocalDate.of(2022, 11, 17), borrower1, corporatePass2, BookingStatus.COLLECTED);
        Booking booking4 = new Booking(LocalDate.of(2022, 10, 18), borrower2, corporatePass1);
        Booking booking5 = new Booking(LocalDate.of(2022, 10, 18), borrower2, corporatePass2);
        Booking booking6 = new Booking(LocalDate.of(2022, 10, 19), borrower2, corporatePass2);
        Booking booking7 = new Booking(LocalDate.of(2022, 11, 11), borrower2, corporatePass5);
        Booking booking8 = new Booking(LocalDate.of(2022, 11, 12), borrower2, corporatePass6);
        Booking booking9 = new Booking(LocalDate.of(2022, 11, 13), borrower2, corporatePass6);

        bookingRepository.saveAllAndFlush(Arrays.asList(booking1, booking2, booking3, booking4, booking5, booking6, booking7, booking8, booking9));

        System.out.println("======TEST BOOKING INSERTED======");
    }
}
