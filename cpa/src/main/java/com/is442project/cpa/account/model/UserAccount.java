package com.is442project.cpa.account.model;

import com.sun.istack.NotNull;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

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

    private boolean isActive;

    @ElementCollection(fetch=FetchType.EAGER)
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
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.password = bCryptPasswordEncoder.encode(password);
        this.contactNumber = contactNumber;
        this.roles = roles;
        this.isActive = true;
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

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }

    public void clearRoles() {
        this.roles.clear();
    }
}
