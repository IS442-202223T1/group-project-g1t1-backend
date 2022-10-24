package com.is442project.cpa.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.is442project.cpa.account.UserAccount;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "corporatePass")
public class CorporatePass {
    Membership membershipType;

    @Id 
    String number;

    String status;
    int maxPersonsAdmitted;

    public CorporatePass(Membership membershipType, String number, String status, int maxPersonsAdmitted){
        this.membershipType = membershipType;
        this.number = number;
        this.status = status;
        this.maxPersonsAdmitted = maxPersonsAdmitted;
    }

    public Membership getMembershipType(){
        return membershipType;
    }

    public void setMembershipType(Membership membershipType){
        this.membershipType = membershipType;
    }

    public String getID(){
        return number;
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
