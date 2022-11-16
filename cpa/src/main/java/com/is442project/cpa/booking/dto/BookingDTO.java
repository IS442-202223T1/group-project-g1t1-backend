package com.is442project.cpa.booking.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingDTO {

    private LocalDate date;

    private String email;

    private String membershipName;
    
    private int quantity;

    public BookingDTO() {
    }

    public BookingDTO(LocalDate date, String email, String membershipName, int quantity) {
        this.date = date;
        this.email = email;
        this.membershipName = membershipName;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "BookingDTO [date=" + date + ", email=" + email + ", membershipName=" + membershipName + ", quantity=" + quantity
                + "]";
    }

}
