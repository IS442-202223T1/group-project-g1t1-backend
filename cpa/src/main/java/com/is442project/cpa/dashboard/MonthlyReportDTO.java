package com.is442project.cpa.dashboard;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MonthlyReportDTO {
    private int numberOfLoans;
    private int numberOfBorrowers;

    public MonthlyReportDTO(){}

    public MonthlyReportDTO(int numberOfLoans, int numberOfBorrowers){
        this.numberOfLoans = numberOfLoans;
        this.numberOfBorrowers = numberOfBorrowers;
    }
}
