package com.is442project.cpa.admin;

import javax.persistence.Embeddable;

@Embeddable
public class Borrower extends Role{
    public Borrower() {
        super("Borrower");
    }
}
