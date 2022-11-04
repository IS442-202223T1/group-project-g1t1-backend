package com.is442project.cpa.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.is442project.cpa.account.Borrower;
import com.is442project.cpa.account.UserAccount;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "booking")
public class Booking {
    
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int bookingId;

    @ManyToOne
    private CorporatePass corporatePass;

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

    public Booking() {
    }

    public Booking(LocalDate borrowDate, UserAccount borrower, CorporatePass corporatePass) {
        this.borrowDate = borrowDate;
        this.borrower = borrower;
        this.corporatePass = corporatePass;
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

    public CorporatePass getCorporatePass() {
        return corporatePass;
    }

    public void setCorporatePass(CorporatePass corporatePass) {
        this.corporatePass = corporatePass;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    public List<Booking> bookPass(LocalDate borrowDate, UserAccount borrower, List<CorporatePass> availPasses, int qty, BookingRepository bookingRepository){
        //todo business logic to check user have exceed booking limit
        //todo business logic to check user have any outstanding dues

        List<Booking> toBookPasses = new ArrayList<>();
        for (int i = 0; i < qty; i++) {
            Booking booking = new Booking(LocalDate.now(), borrower, availPasses.get(i));
            toBookPasses.add(booking);
        }

        return bookingRepository.saveAllAndFlush(toBookPasses);
    }
}
