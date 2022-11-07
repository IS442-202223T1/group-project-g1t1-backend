package com.is442project.cpa.booking;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BorrowerOps {
    public abstract ResponseEntity<BookingResponseDto> bookPass(BookingDto bookingDto);

    public abstract BookingResponseDto cancelBooking(int bookingID);

    public abstract  List<BookingResponseDto> getAllBooking(String userID);

    public abstract CorporatePass reportLost(String corporatePassNumber);

    public abstract List<Membership> getAllAttractions();
}
