package com.is442project.cpa.booking;

import java.time.LocalDate;

public class BookingDto {
    public LocalDate date;
    public String email;
    public int requestedBookings;

    @Override
    public String toString() {
        return "BookingDto [date=" + date + ", email=" + email + ", requestedBookings=" + requestedBookings + "]";
    }
    
}
