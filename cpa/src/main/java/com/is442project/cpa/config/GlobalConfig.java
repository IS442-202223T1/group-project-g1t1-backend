package com.is442project.cpa.config;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "global_config")
public class GlobalConfig {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id; 
    private int loanLimitPerMonth;
    private int passLimitPerLoan;
    private String letterHeadUrl;
    private String corporateMemberName;

    public GlobalConfig() {
    }

    public GlobalConfig(int loanLimitPerMonth, int passLimitPerLoan, String letterHeadUrl,
            String corporateMemberName) {
        this.loanLimitPerMonth = loanLimitPerMonth;
        this.passLimitPerLoan = passLimitPerLoan;
        this.letterHeadUrl = letterHeadUrl;
        this.corporateMemberName = corporateMemberName;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    } 

    public int getLoanLimitPerMonth() {
        return loanLimitPerMonth;
    }

    public void setLoanLimitPerMonth(int loanLimitPerMonth) {
        this.loanLimitPerMonth = loanLimitPerMonth;
    }

    public int getPassLimitPerLoan() {
        return passLimitPerLoan;
    }

    public void setPassLimitPerLoan(int passLimitPerLoan) {
        this.passLimitPerLoan = passLimitPerLoan;
    }

    public String getLetterHeadUrl() {
        return letterHeadUrl;
    }

    public void setLetterHeadUrl(String letterHeadUrl) {
        this.letterHeadUrl = letterHeadUrl;
    }

    public String getCorporateMemberName() {
        return corporateMemberName;
    }

    public void setCorporateMemberName(String corporateMemberName) {
        this.corporateMemberName = corporateMemberName;
    }
}