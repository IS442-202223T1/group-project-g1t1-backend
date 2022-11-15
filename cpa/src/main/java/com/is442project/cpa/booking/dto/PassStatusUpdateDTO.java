package com.is442project.cpa.booking.dto;

import lombok.Data;

@Data
public class PassStatusUpdateDTO {

    private String bookingID;

    private String actionToPerform;

    public PassStatusUpdateDTO(String bookingID, String actionToPerform){
        this.bookingID = bookingID;
        this.actionToPerform = actionToPerform;
    }

}
