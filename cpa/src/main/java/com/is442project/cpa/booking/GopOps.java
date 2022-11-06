package com.is442project.cpa.booking;

import java.util.List;

interface GopOps {
//    public abstract List<BookingDto> getCurrentBooking();

//    public abstract List<BookingDto> getPastBooking();

    public abstract boolean collectCard(Long cardId);

    public abstract double returnCard(Long cardId);

    public abstract double markLost(Long cardId);

    public abstract List<CorporatePass> getAllPasses();
}
