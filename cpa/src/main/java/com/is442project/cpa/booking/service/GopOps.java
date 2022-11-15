package com.is442project.cpa.booking.service;

import java.util.List;

import com.is442project.cpa.booking.model.Booking;
import com.is442project.cpa.booking.model.CorporatePass;

public interface GopOps {

    public abstract boolean updateBookingStatus(int bookingID, String actionToPerform);

    public abstract List<CorporatePass> getAllPasses();

    public abstract List<Booking> getBookingsByEmail(String email);
    
}
