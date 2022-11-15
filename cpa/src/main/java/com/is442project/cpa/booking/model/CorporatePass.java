package com.is442project.cpa.booking.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "corporatePass")
public class CorporatePass {
    
    public enum Status {
        LOST, AVAILABLE, LOANED
    }

    @Id
    @GeneratedValue
    Long id;

    @NotNull
    String passID;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "membershipName")
    Membership membership;

    @NotNull
    Status status;

    @NotNull
    int maxPersonsAdmitted;

    @NotNull
    boolean isActive;

    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDate expiryDate;

    public CorporatePass(){}

    public CorporatePass(Membership membership, String passID, Status status, int maxPersonsAdmitted) {
        this.membership = membership;
        this.passID = passID;
        this.status = status;
        this.maxPersonsAdmitted = maxPersonsAdmitted;
        this.isActive = true;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassID() {
        return passID;
    }

    public void setPassID(String passID) {
        this.passID = passID;
    }

    public int getMaxPersonsAdmitted() {
        return maxPersonsAdmitted;
    }

    public void setMaxPersonsAdmitted(int maxPersonsAdmitted) {
        this.maxPersonsAdmitted = maxPersonsAdmitted;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive){
        this.isActive = isActive;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

}
