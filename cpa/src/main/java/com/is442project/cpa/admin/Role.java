package com.is442project.cpa.admin;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public abstract class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roleId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_account_email")
    @NotNull
    UserAccount userAccount;
    String label;
    public Role(String label) {
        this.label = label;
    }

    public UserAccount getUser() {
        return userAccount;
    }

    public void setUser(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
