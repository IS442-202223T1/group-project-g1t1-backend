package com.is442project.cpa.booking.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

import com.is442project.cpa.booking.model.Booking;

@Data
public class BookingEmailDTO {

    private LocalDate date;

    private String email;

    private String membershipName;
    
    private int quantity;

    private List<Booking> bookingResults;

}
