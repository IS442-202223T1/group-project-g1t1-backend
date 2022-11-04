package com.is442project.cpa.booking;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class BookingDto {
    private LocalDate Date;
    private String email;

    private String membershipId;

    private int qty;


    public BookingDto() {
    }

    public BookingDto(LocalDate date, String email, String membershipId, int qty) {
        Date = date;
        this.email = email;
        this.membershipId = membershipId;
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "BookingDto{" +
                "Date=" + Date +
                ", email='" + email + '\'' +
                '}';
    }
}
