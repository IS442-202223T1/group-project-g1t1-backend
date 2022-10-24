package com.is442project.cpa.booking;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "membership")
public class Membership {
    private String membershipType;

    public Membership(String membershipType){
        this.membershipType = membershipType;
    };

    public String getMembershipType(){
        return membershipType;
    }

    public void setMembershipType(String membershipType){
        this.membershipType = membershipType;
    }
}
