package com.is442project.cpa.booking;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class BookingDto {
    private LocalDate date;
    private String email;

    private String membershipId;

    private int qty;


    public BookingDto() {
    }

    public BookingDto(LocalDate date, String email, String membershipId, int qty) {
        this.date = date;
        this.email = email;
        this.membershipId = membershipId;
        this.qty = qty;
    }

    public LocalDate getDate(){
        return date;
    }

    public String getEmail(){
        return email;
    }

    public String getMembershipType(){
        return membershipId;
    }

    public int getQuantity(){
        return qty;
    }

    @Override
    public String toString() {
        return "BookingDto{" +
                "Date=" + date +
                ", email='" + email + '\'' +
                '}';
    }
}
