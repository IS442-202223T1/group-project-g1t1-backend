package com.is442project.cpa.account.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ProfileDTO {

    private String name;

    private String email;

    private String contactNumber;

    private List<String> Roles = new ArrayList<>();

    public ProfileDTO() {
    }

    public ProfileDTO(String name, String email, String contactNumber, List<String> roles) {
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        Roles = roles;
    }

}
