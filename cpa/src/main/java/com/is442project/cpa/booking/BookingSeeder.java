package com.is442project.cpa.booking;

import com.is442project.cpa.account.AccountService;
import com.is442project.cpa.account.UserAccount;
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

        Booking booking1 = new Booking();
        booking1.setBorrower(borrower1);
        booking1.setBorrowDate(LocalDate.of(2022, 11, 16));
        booking1.setCorporatePasses(Arrays.asList(corporatePass1));

        bookingRepository.saveAllAndFlush(Arrays.asList(booking1));

        System.out.println("======TEST BOOKING INSERTED======");
    }
}
