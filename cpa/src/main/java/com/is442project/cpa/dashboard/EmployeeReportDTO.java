package com.is442project.cpa.dashboard;

import lombok.Data;
@Data
public class EmployeeReportDTO {
    private int numberOfLoans;

    public EmployeeReportDTO(){}

    public EmployeeReportDTO(int numberOfLoans){
        this.numberOfLoans = numberOfLoans;
    }
}
