package com.is442project.cpa.booking;

import java.time.LocalDate;
import java.util.List;

public interface BorrowerOps {
    public abstract boolean bookPass(BookingDTO bookingDTO);

    public abstract BookingResponseDTO cancelBooking(String bookingID);

    public abstract  List<BookingResponseDTO> getAllBooking(String userID);

    public abstract CorporatePass reportLost(String corporatePassID);

    public abstract List<Booking> getBookingsByDayAndMembership(LocalDate date, String membershipName);
}
