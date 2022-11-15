package com.is442project.cpa.config.dto;

import lombok.Data;

@Data
public class ConfigDTO {

    private int loanLimitPerMonth;

    private int passLimitPerLoan;

    private String letterHeadUrl;

    private String corporateMemberName;
    
}
