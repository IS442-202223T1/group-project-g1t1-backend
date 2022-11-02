package com.is442project.cpa.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.is442project.cpa.account.UserAccount;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "booking")
public class Booking {
    
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int bookingId;

//    @ManyToOne
//    @JoinColumn(name = "corpPass")
//    private CorporatePass CorporatePass;

//    private Membership membershipType;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate borrowDate;

    @ManyToOne
    @JoinColumn(name = "email")
    private UserAccount borrower;

    private boolean isCollected;
    private boolean isReturned;
    private boolean isLost;

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public Booking(LocalDate borrowDate, UserAccount borrower) {
        this.borrowDate = borrowDate;
        this.borrower = borrower;
    }

    //    public com.is442project.cpa.booking.CorporatePass getCorporatePass() {
//        return CorporatePass;
//    }
//
//    public void setCorporatePass(com.is442project.cpa.booking.CorporatePass corporatePass) {
//        CorporatePass = corporatePass;
//    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public UserAccount getBorrower() {
        return borrower;
    }

    public void setBorrower(UserAccount borrower) {
        this.borrower = borrower;
    }

    public boolean isIssued() {
        return isCollected;
    }

    public void setIssued(boolean issued) {
        isCollected = issued;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }

    public boolean isLost() {
        return isLost;
    }

    public void setLost(boolean lost) {
        isLost = lost;
    }


}
