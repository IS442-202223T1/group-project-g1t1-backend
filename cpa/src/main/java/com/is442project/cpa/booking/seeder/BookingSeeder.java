package com.is442project.cpa.booking.seeder;

import com.is442project.cpa.account.model.Borrower;
import com.is442project.cpa.account.model.Role;
import com.is442project.cpa.account.model.UserAccount;
import com.is442project.cpa.account.model.UserAccountRepository;
import com.is442project.cpa.booking.model.Booking;
import com.is442project.cpa.booking.model.BookingRepository;
import com.is442project.cpa.booking.model.CorporatePass;
import com.is442project.cpa.booking.model.CorporatePassRepository;
import com.is442project.cpa.booking.model.Booking.BookingStatus;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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


        CorporatePass corporatePass1 = corporatePassRepository.findById(Long.parseLong("1")).get();
        CorporatePass corporatePass2 = corporatePassRepository.findById(Long.parseLong("2")).get();
        CorporatePass corporatePass3 = corporatePassRepository.findById(Long.parseLong("2")).get();

        List<Booking> bookingList = new ArrayList<>();

        Booking booking1 = new Booking(LocalDate.of(2022, 12, 6), borrower1, corporatePass1, BookingStatus.CONFIRMED);
        Booking booking2 = new Booking(LocalDate.of(2022, 12, 6), borrower2, corporatePass2, BookingStatus.CONFIRMED);
        bookingList.add(booking1);
        bookingList.add(booking2);

        Random random = new Random();
        List<UserAccount> users = accountRepository.findAll();
        users = users.stream().filter(userAccount -> userAccount.getRoles().contains(new Borrower())).collect(Collectors.toList());
        List<CorporatePass> passes = corporatePassRepository.findAll();

        Booking randomBooking;
        for (int month = 1; month <= 11 ; month++) {
            for (int day = 1; day <= 28; day++) {
                for (int i = 0; i < random.nextInt(passes.size()); i++) {
                    randomBooking = new Booking(LocalDate.of(2022, month, day), users.get(random.nextInt(users.size())), passes.get(i), BookingStatus.RETURNED);
                    bookingList.add(randomBooking);

                    if(month == LocalDate.now().getMonthValue() && day > LocalDate.now().getDayOfMonth()) {
                        randomBooking.setBookingStatus(BookingStatus.CONFIRMED);
                    }
                }
            }
        }

        bookingList.get(50).setCorporatePass(corporatePass3);
        bookingList.get(50).setBookingStatus(BookingStatus.DUESOWED);
        bookingList.get(50).setFeesOwed(53.50);

        for (int i = 51; i < bookingList.size(); i++) {
            if (bookingList.get(i).getCorporatePass().getId() == corporatePass3.getId()) {
                bookingList.get(i).setBookingStatus(BookingStatus.CANCELLED);
            }
        }

        bookingRepository.saveAllAndFlush(bookingList);

        System.out.println("======TEST BOOKINGS INSERTED======");

    }

}
