package com.is442project.cpa.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.is442project.cpa.account.UserAccount;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "booking")
public class Booking {

    public enum BookingStatus {
        CONFIRMED, COLLECTED, CANCELLED, RETURNED, DUESOWED, DUESPAID
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int bookingId;

    @ManyToOne
    @JoinColumn(name = "CPID")
    private CorporatePass corporatePass;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate borrowDate;

    @ManyToOne
    @JoinColumn(name = "email")
    private UserAccount borrower;

    @NotNull
    @Column(length = 32, columnDefinition = "varchar(255) default 'CONFIRMED'")
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus = BookingStatus.CONFIRMED;

    private double feesOwed;

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public Booking() {
    }

    public Booking(LocalDate borrowDate, UserAccount borrower, CorporatePass corporatePass) {
        this.borrowDate = borrowDate;
        this.borrower = borrower;
        this.corporatePass = corporatePass;
    }

    public Booking(LocalDate borrowDate, UserAccount borrower, CorporatePass corporatePass, BookingStatus bookingStatus) {
        this.borrowDate = borrowDate;
        this.borrower = borrower;
        this.corporatePass = corporatePass;
        this.bookingStatus = bookingStatus;
        this.feesOwed = 0;
    }

    public Booking(LocalDate borrowDate, UserAccount borrower, CorporatePass corporatePass, BookingStatus bookingStatus, Double feesOwed) {
        this.borrowDate = borrowDate;
        this.borrower = borrower;
        this.corporatePass = corporatePass;
        this.bookingStatus = bookingStatus;
        this.feesOwed = feesOwed;
    }

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

    public CorporatePass getCorporatePass() {
        return corporatePass;
    }

    public void setCorporatePass(CorporatePass corporatePass) {
        this.corporatePass = corporatePass;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public double getFeesOwed(){
        return feesOwed;
    }

    public void setFeesOwed(double feesOwed){
        this.feesOwed = feesOwed;
    }
}
