package com.is442project.cpa.booking;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class BookingDTO {
    private LocalDate Date;
    private String email;

    private String membershipName;

    private int qty;


    public BookingDTO() {
    }

    public BookingDTO(LocalDate date, String email, String membershipId, int qty) {
        Date = date;
        this.email = email;
        this.membershipName = membershipName;
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
