package com.is442project.cpa.booking;

import java.util.List;

public interface BorrowerOps {
    public abstract List<Booking> bookPass(BookingDTO bookingDTO);

    public abstract BookingResponseDTO cancelBooking(String bookingID);

    public abstract  List<BookingResponseDTO> getAllBooking(String userID);

    public abstract CorporatePass reportLost(String corporatePassID);

    public abstract List<Booking> getBookingsByDayAndMembership(BookingDTO bookingDTO);
}
