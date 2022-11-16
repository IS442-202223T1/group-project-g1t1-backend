package com.is442project.cpa.booking.dto;

import lombok.Data;

@Data
public class BookingIDDTO {
    
    private int bookingID;

    public BookingIDDTO() {
    }

    public BookingIDDTO(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getBookingID(){
        return bookingID;
    }

}
