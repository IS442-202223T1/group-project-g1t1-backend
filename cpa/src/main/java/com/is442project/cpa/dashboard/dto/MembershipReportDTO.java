package com.is442project.cpa.dashboard.dto;

import lombok.Data;

@Data
public class MembershipReportDTO {

    private String month;

    private String year;

    private int numberOfLoans;

    private int numberOfBorrowers;

    public MembershipReportDTO(){}

    public MembershipReportDTO(String month, String year, int numberOfLoans, int numberOfBorrowers) {
        this.month = month;
        this.year = year;
        this.numberOfLoans = numberOfLoans;
        this.numberOfBorrowers = numberOfBorrowers;
    }
}
