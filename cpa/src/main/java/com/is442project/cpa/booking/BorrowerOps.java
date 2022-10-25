package com.is442project.cpa.booking;

import org.springframework.http.ResponseEntity;

import com.is442project.cpa.account.UserAccount;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface BorrowerOps {
    public abstract ArrayList<Booking> bookPass(BookingDto bookingDto);

    public abstract boolean checkExceedBookingLimit(BookingDto bookingDto);

    public abstract boolean checkInsufficientPass(BookingDto bookingDto);

    public abstract BookingResponseDto cancelBooking(String bookingID);

    public abstract  List<BookingResponseDto> getAllBooking(String userID);

    public abstract CorporatePass reportLost(String corporatePassNumber);

}
