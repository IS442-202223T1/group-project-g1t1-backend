package com.is442project.cpa.admin;

import java.util.ArrayList;
import java.util.List;

public class ProfileDto {
    private String name;
    private String email;
    private String contactNumber;
    private List<String> Roles = new ArrayList<>();

    public ProfileDto() {
    }

    public ProfileDto(String name, String email, String contactNumber, List<String> roles) {
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        Roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public List<String> getRoles() {
        return Roles;
    }

    public void setRoles(List<String> roles) {
        Roles = roles;
    }
}
