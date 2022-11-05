package com.is442project.cpa.booking;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "corporatePass")
public class CorporatePass {
    @Id
    @GeneratedValue
    Long id;

    @NotNull
    String number;
    @ManyToOne
    @JoinColumn(name = "membershipType")
    Membership membershipType;

    String status;
    int maxPersonsAdmitted;

    public CorporatePass(Membership membershipType, String number, String status, int maxPersonsAdmitted){
        this.membershipType = membershipType;
        this.number = number;
        this.status = status;
        this.maxPersonsAdmitted = maxPersonsAdmitted;
    }

    public CorporatePass(){

    }

    public Membership getMembershipType(){
        return membershipType;
    }

    public void setMembershipType(Membership membershipType){
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

    public String getStatus(){
        return status;
    }

    public String setStatus(String status){
        this.status = status;
        return status;
    }

    public int getNumberAdmitted(){
        return maxPersonsAdmitted;
    }

    public void setNumberAdmitted(int maxPersonsAdmitted){
        this.maxPersonsAdmitted = maxPersonsAdmitted;
    }
}
