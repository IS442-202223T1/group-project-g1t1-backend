package com.is442project.cpa.booking;

import java.util.List;

interface GopOps {
//    public abstract List<BookingDto> getCurrentBooking();

//    public abstract List<BookingDto> getPastBooking();

    public abstract boolean collectCard(Long cardId);

    public abstract double returnCard(int bookingID);

    public abstract double markLost(int bookingID);

    public abstract List<CorporatePass> getAllPasses();
}
