package com.is442project.cpa.booking;

import java.util.List;

interface GopOps {
//    public abstract List<BookingDto> getCurrentBooking();

//    public abstract List<BookingDto> getPastBooking();

    public abstract boolean updateBookingStatus(int bookingID, String actionToPerform);

    public abstract List<CorporatePass> getAllPasses();

    public abstract List<Booking> getAllOpenBookings();
}
