package com.is442project.cpa.booking;

import java.time.LocalDate;

import com.is442project.cpa.booking.Booking.BookingStatus;

import lombok.Data;

@Data
public class BookingResponseDTO {

    private int bookingID;
    private LocalDate borrowDate;
    private BookingStatus bookingStatus;

    private String passId;
    private int maxPersonsAdmitted;
    
    private String imageUrl;
    private String membershipName;
    
    private boolean isSunday;
    private LocalDate previousBookingDate;
    private String previousBookerName; 
    private String previousBookerContactNumber;

    public BookingResponseDTO() {
    }

}
