package com.is442project.cpa.booking;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BorrowerOps {
    public abstract ResponseEntity<BookingResponseDTO> bookPass(BookingDTO bookingDTO);

    public abstract BookingResponseDTO cancelBooking(BookingIDDTO bookingIDDTO);

    public abstract  List<BookingResponseDTO> getAllBooking(String userID);

    public abstract CorporatePass reportLost(String corporatePassID);

    public abstract List<Membership> getAllAttractions();

    public abstract List<Booking> getAvailableBookingByUser(String email);
}
