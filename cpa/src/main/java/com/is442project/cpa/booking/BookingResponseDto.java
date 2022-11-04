package com.is442project.cpa.booking;

public class BookingResponseDto {
    private String bookingId;
    private CorporatePass corpPass;

    public BookingResponseDto() {
    }

    public BookingResponseDto(Booking booking) {
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
