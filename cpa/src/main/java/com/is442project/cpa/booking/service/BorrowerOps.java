package com.is442project.cpa.booking.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.is442project.cpa.booking.dto.BookingDTO;
import com.is442project.cpa.booking.dto.BookingEmailDTO;
import com.is442project.cpa.booking.dto.BookingResponseDTO;
import com.is442project.cpa.booking.model.Booking;
import com.is442project.cpa.booking.model.CorporatePass;
import com.is442project.cpa.booking.model.Membership;

public interface BorrowerOps {

    public abstract List<Booking> bookPass(BookingDTO bookingDTO);

    public abstract boolean cancelBooking(int bookingID);

    public abstract Membership getMembershipByName(String membershipName);

    public abstract List<Booking> getBookingsByDayAndMembership(LocalDate date, String membershipName);

    public abstract List<Membership> getAllMemberships();

    public abstract List<Membership> getAllActiveMemberships();

    public abstract List<BookingResponseDTO> getUpcomingBookings(String email);

    public abstract List<BookingResponseDTO> getPastBookings(String email);

    public abstract HashMap<LocalDate, HashSet<CorporatePass>> getAvailableBooking(LocalDate startDate, LocalDate endDate);

    public abstract boolean sendEmail(BookingEmailDTO bookingEmailDTO);
    
}
