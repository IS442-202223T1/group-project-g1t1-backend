package com.is442project.cpa.booking;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
}
