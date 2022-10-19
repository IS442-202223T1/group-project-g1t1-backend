package com.is442project.cpa.booking;

import com.is442project.cpa.admin.UserAccount;

import java.time.LocalDate;

public class BookingDto {
    public LocalDate Date;
    public UserAccount Borrower;

    @Override
    public String toString() {
        return "BookingDto{" +
                "Date=" + Date +
                ", Borrower=" + Borrower +
                '}';
    }
}
