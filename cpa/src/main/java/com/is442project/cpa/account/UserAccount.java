package com.is442project.cpa.account;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class UserAccount {

    @Id
    private String email;

    /* intended to be replicate of email for org.springframework.security.core.Authentication
    which relies on the attribute `username` to be present */
    private String username;

    @NotNull
    private String name;

    private String password;

    private String contactNumber;

    @ElementCollection
    private List<Role> roles = new ArrayList<>();

    public UserAccount() {
    }

    public UserAccount(String email, String name) {
        this(email, name, String.valueOf(UUID.randomUUID()),"", Arrays.asList(new Borrower()));
    }

    public UserAccount(String email, String name, String password, String contactNumber , List<Role> roles) {
        this.email = email;
        this.username = email;
        this.name = name;
        this.password = password;
        this.contactNumber = contactNumber;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.username = email;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
