package com.is442project.cpa.booking;

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

    // @Override
    // public String toString() {
    //     return "BookingDto{" +
    //             "Date=" + Date +
    //             ", email='" + email + '\'' +
    //             '}';
    // }
}
