package com.is442project.cpa.booking;

import java.time.LocalDate;

public class BookingDto {
    public LocalDate Date;
    public String email;

    @Override
    public String toString() {
        return "BookingDto{" +
                "Date=" + Date +
                ", email='" + email + '\'' +
                '}';
    }
}
