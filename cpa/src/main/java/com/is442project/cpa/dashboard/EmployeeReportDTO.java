package com.is442project.cpa.dashboard;

import lombok.Data;

@Data
public class EmployeeReportDTO {
    private String employeeName;
    private String employeeEmail;
    private int numberOfLoans;

    public EmployeeReportDTO(){}

    public EmployeeReportDTO(String employeeName, String employeeEmail, int numberOfLoans) {
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.numberOfLoans = numberOfLoans;
    }

    
}
