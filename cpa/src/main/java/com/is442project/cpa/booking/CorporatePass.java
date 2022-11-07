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
    String number;
    @NotNull
    String membershipType;

    Status status;
    int maxPersonsAdmitted;
    String passType;

    public CorporatePass(String membershipType, String number, Status status, int maxPersonsAdmitted, String passType) {
        this.membershipType = membershipType;
        this.number = number;
        this.status = status;
        this.maxPersonsAdmitted = maxPersonsAdmitted;
        this.passType = passType;
    }

    public CorporatePass() {

    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public int getNumberAdmitted() {
        return maxPersonsAdmitted;
    }

    public void setNumberAdmitted(int maxPersonsAdmitted) {
        this.maxPersonsAdmitted = maxPersonsAdmitted;
    }

    public String getPassType() {
        return passType;
    }

    public void setPassType(String passType) {
        this.passType = passType;
    }
}
