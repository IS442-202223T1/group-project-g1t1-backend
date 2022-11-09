package com.is442project.cpa.booking;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.is442project.cpa.common.template.Template;

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

    @ManyToOne
    @JoinColumn(name = "membership")
    Membership membership;

    @NotNull
    Status status;

    @NotNull
    int maxPersonsAdmitted;

    public CorporatePass(){}

    public CorporatePass(Membership membership, String passID, Status status, int maxPersonsAdmitted) {
        this.membership = membership;
        this.passID = passID;
        this.status = status;
        this.maxPersonsAdmitted = maxPersonsAdmitted;
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

    public double getLostFees(){
        return membership.getReplacementFee();
        // return 0.0;
    }
}
