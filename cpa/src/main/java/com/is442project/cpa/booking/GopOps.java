package com.is442project.cpa.booking;

import java.util.List;

interface GopOps {
//    public abstract List<BookingDto> getCurrentBooking();

//    public abstract List<BookingDto> getPastBooking();

    public abstract boolean collectCard(String cardId);

    public abstract boolean returnCard(String cardId);

    public abstract boolean markLost(String cardId);

    public abstract List<CorporatePass> getAllPasses();
}
