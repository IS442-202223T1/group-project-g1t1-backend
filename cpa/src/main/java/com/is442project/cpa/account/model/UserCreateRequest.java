package com.is442project.cpa.account.model;

import lombok.Data;

@Data
public class UserCreateRequest {
    private String email;
    private String name;
    private String contactNumber;
    private String password;

    public UserCreateRequest(String email, String name, String contactNumber, String password) {
        this.email = email;
        this.name = name;
        this.contactNumber = contactNumber;
        this.password = password;
    }

    public UserCreateRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
