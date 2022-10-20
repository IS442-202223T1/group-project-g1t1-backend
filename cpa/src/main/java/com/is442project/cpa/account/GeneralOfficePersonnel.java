package com.is442project.cpa.account;

import javax.persistence.Embeddable;

@Embeddable
public class GeneralOfficePersonnel extends Role{
    public GeneralOfficePersonnel() {
        super("gop");
    }
}
