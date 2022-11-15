package com.is442project.cpa.booking;

import java.time.LocalDate;
import java.util.List;

public interface BorrowerOps {
    public abstract boolean bookPass(BookingDTO bookingDTO);

    public abstract boolean cancelBooking(int bookingID);

    public abstract  List<BookingResponseDTO> getAllBooking(String userID);

    public abstract Membership getMembershipByName(String membershipName);

    public abstract List<Booking> getBookingsByDayAndMembership(LocalDate date, String membershipName);

    public abstract List<Membership> getAllMemberships();

    public abstract List<Membership> getAllActiveMemberships();

    public abstract List<BookingResponseDTO> getUpcomingBookings(String email);

    public abstract List<BookingResponseDTO> getPastBookings(String email);
}
