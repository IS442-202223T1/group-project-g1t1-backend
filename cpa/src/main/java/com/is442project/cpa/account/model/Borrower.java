package com.is442project.cpa.account.model;

import javax.persistence.Embeddable;

@Embeddable
public class Borrower extends Role{
    public Borrower() {
        super("borrower");
    }
}
