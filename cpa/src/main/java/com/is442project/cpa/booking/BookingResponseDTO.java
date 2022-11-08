package com.is442project.cpa.booking;

public class BookingResponseDTO {
    private String bookingId;
    private CorporatePass corpPass;

    public BookingResponseDTO() {
    }

    public BookingResponseDTO(Booking booking) {
        bookingId = ""+booking.getBookingId();
        corpPass = booking.getCorporatePass();
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public CorporatePass getCorpPass() {
        return corpPass;
    }

    public void setCorpPass(CorporatePass corpPass) {
        this.corpPass = corpPass;
    }
}
