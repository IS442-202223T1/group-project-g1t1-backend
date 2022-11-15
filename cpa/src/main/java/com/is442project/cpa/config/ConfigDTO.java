package com.is442project.cpa.config;

import lombok.Data;

@Data
public class ConfigDTO {
    private int loanLimitPerMonth;
    private int passLimitPerLoan;
    private String letterHeadUrl;
    private String corporateMemberName;
}
