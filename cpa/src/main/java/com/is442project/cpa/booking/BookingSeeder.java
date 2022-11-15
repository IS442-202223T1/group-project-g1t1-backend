package com.is442project.cpa.booking;

import com.is442project.cpa.account.UserAccount;
import com.is442project.cpa.account.UserAccountRepository;
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

    private final CorporatePassRepository corporatePassRepository;

    private BookingRepository bookingRepository;

    private UserAccountRepository accountRepository;
    public BookingSeeder(CorporatePassRepository corporatePassRepository, BookingRepository bookingRepository, UserAccountRepository accountRepository) {
        this.corporatePassRepository = corporatePassRepository;
        this.bookingRepository = bookingRepository;
        this.accountRepository = accountRepository;
        insertTestBooking();
    }

    private void insertTestBooking(){
        UserAccount borrower1 = accountRepository.getReferenceById("joshua.zhangzy@gmail.com");
        UserAccount borrower2 = accountRepository.getReferenceById("mary@nysi.org.sg");
        UserAccount borrower3 = accountRepository.getReferenceById("david@sportsschool.edu.sg");
        UserAccount borrower4 = accountRepository.getReferenceById("brotherbear@gmail.com");

        CorporatePass corporatePass1 = corporatePassRepository.findById(Long.parseLong("1")).get();
        CorporatePass corporatePass2 = corporatePassRepository.findById(Long.parseLong("2")).get();
        CorporatePass corporatePass5 = corporatePassRepository.findById(Long.parseLong("5")).get();
        CorporatePass corporatePass6 = corporatePassRepository.findById(Long.parseLong("6")).get();
        CorporatePass corporatePass7 = corporatePassRepository.findById(Long.parseLong("7")).get();

        CorporatePass corporatePass9 = corporatePassRepository.findById(Long.parseLong("9")).get();
        CorporatePass corporatePass10 = corporatePassRepository.findById(Long.parseLong("10")).get();
        CorporatePass corporatePass11 = corporatePassRepository.findById(Long.parseLong("11")).get();


        Booking booking1 = new Booking(LocalDate.of(2022, 8, 12), borrower1, corporatePass1, BookingStatus.COLLECTED);
        Booking booking2 = new Booking(LocalDate.of(2022, 11, 16), borrower1, corporatePass9, BookingStatus.CONFIRMED);
        Booking booking3 = new Booking(LocalDate.of(2022, 11, 16), borrower1, corporatePass10, BookingStatus.CONFIRMED);
        Booking booking4 = new Booking(LocalDate.of(2022, 10, 18), borrower2, corporatePass1, BookingStatus.COLLECTED);
        Booking booking5 = new Booking(LocalDate.of(2022, 10, 18), borrower2, corporatePass2, BookingStatus.COLLECTED);
        Booking booking6 = new Booking(LocalDate.of(2022, 10, 19), borrower2, corporatePass2, BookingStatus.CONFIRMED);
        Booking booking7 = new Booking(LocalDate.of(2022, 11, 06), borrower2, corporatePass5, BookingStatus.CANCELLED);
        Booking booking8 = new Booking(LocalDate.of(2022, 11, 11), borrower2, corporatePass5, BookingStatus.CANCELLED);
        Booking booking9 = new Booking(LocalDate.of(2022, 11, 11), borrower2, corporatePass7, BookingStatus.CONFIRMED);
        Booking booking10 = new Booking(LocalDate.of(2022, 11, 13), borrower3, corporatePass5, BookingStatus.COLLECTED);
        Booking booking11 = new Booking(LocalDate.of(2022, 11, 15), borrower3, corporatePass5, BookingStatus.COLLECTED);
        Booking booking12 = new Booking(LocalDate.of(2022,10,12), borrower4, corporatePass6, BookingStatus.DUESOWED, 54.0);
        Booking booking100 = new Booking(LocalDate.of(2022, 12, 16), borrower1, corporatePass1, BookingStatus.CONFIRMED);


        bookingRepository.saveAllAndFlush(Arrays.asList(booking1, booking2, booking3, booking4, booking5, booking6, booking7, booking8, booking9, booking10, booking11, booking12, booking100));

        System.out.println("======TEST BOOKING INSERTED======");
    }
}
