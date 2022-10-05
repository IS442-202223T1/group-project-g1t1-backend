package com.is442project.cpa.admin;

import javax.persistence.Entity;

@Entity
public class Administrator extends Role{
    public Administrator() {
        super("Admin");
    }
}
