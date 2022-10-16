package com.is442project.cpa.admin;

import javax.persistence.Embeddable;

@Embeddable
public class Administrator extends Role{
    public Administrator() {
        super("Admin");
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
