package com.is442project.cpa.booking;

import lombok.Data;

@Data

public class BookingOpsData {
    private String bookingID;
    private String actionToPerform;

    public BookingOpsData(String bookingID, String actionToPerform){
        this.bookingID = bookingID;
        this.actionToPerform = actionToPerform;
    }

    public String getBookingID(){
        return bookingID;
    }

    public String getActionToPerform(){
        return actionToPerform;
    }
}
