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

    private double feesDue;
    private String status;

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

    public double getFeesDue(){
        return feesDue;
    }

    public void setFeesDue(double fees){
        feesDue = fees;
    }

    public void clearDues(){
        feesDue = 0; 
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }
}
