package com.is442project.cpa.booking;

import lombok.Data;

@Data
public class BookerDetailsResponseDTO {
    private String bookerName; 
    private String contactNumber;
    private String passId;
    
    public BookerDetailsResponseDTO(String bookerName, String contactNumber, String passId) {
        this.bookerName = bookerName;
        this.contactNumber = contactNumber;
        this.passId = passId;
    }

}
