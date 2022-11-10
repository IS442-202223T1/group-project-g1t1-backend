package com.is442project.cpa.booking;

public class BookingDetailsResponseDTO {
    private String bookerName; 
    private String contactNumber;
    private String passId;
    
    public BookingDetailsResponseDTO(String bookerName, String contactNumber, String passId) {
        this.bookerName = bookerName;
        this.contactNumber = contactNumber;
        this.passId = passId;
    }

    public String getBookerName() {
        return bookerName;
    }

    public void setBookerName(String bookerName) {
        this.bookerName = bookerName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPassId() {
        return passId;
    }

    public void setPassId(String passId) {
        this.passId = passId;
    }

}
