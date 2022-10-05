package com.is442project.cpa.admin;

import javax.persistence.Entity;

@Entity
public class Borrower extends Role{

    private String contactNumber;
    private boolean isDisabled;

    public Borrower() {
        this("", false);
    }

    public Borrower(String contactNumber, boolean isDisabled) {
        super("Borrower");
        this.contactNumber = contactNumber;
        this.isDisabled = isDisabled;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }
}
