package com.is442project.cpa.booking.service;

import java.time.LocalDate;
import java.util.List;

import com.is442project.cpa.booking.dto.BookingDTO;
import com.is442project.cpa.booking.dto.BookingResponseDTO;
import com.is442project.cpa.booking.model.Booking;
import com.is442project.cpa.booking.model.Membership;

public interface BorrowerOps {

    public abstract boolean bookPass(BookingDTO bookingDTO);

    public abstract boolean cancelBooking(int bookingID);

    public abstract Membership getMembershipByName(String membershipName);

    public abstract List<Booking> getBookingsByDayAndMembership(LocalDate date, String membershipName);

    public abstract List<Membership> getAllMemberships();

    public abstract List<BookingResponseDTO> getUpcomingBookings(String email);

    public abstract List<BookingResponseDTO> getPastBookings(String email);
    
}
