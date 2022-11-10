package com.is442project.cpa.config;

import com.is442project.cpa.booking.BookingConfig;
import com.is442project.cpa.booking.BookingConfigRepository;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class GlobalSettings {
    private final BookingConfigRepository bookingConfigRepository;

    public GlobalSettings(BookingConfigRepository bookingConfigRepository) {
        this.bookingConfigRepository = bookingConfigRepository;
        setInitialSate();
    }

    private void setInitialSate() {
        BookingConfig loanPerMonthLimited = new BookingConfig("LoanLimit", "2");

        BookingConfig passesPerLoanLimited = new BookingConfig("PassLimit", "2");

        bookingConfigRepository.saveAll(Arrays.asList(loanPerMonthLimited, passesPerLoanLimited));
    }
}
