package com.is442project.cpa.admin;

import javax.persistence.Embeddable;

@Embeddable
public class GeneralOfficePersonnel extends Role{
    public GeneralOfficePersonnel() {
        super("GOP");
    }
}
