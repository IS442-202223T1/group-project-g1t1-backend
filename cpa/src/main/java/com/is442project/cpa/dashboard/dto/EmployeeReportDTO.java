package com.is442project.cpa.dashboard.dto;

import lombok.Data;

@Data
public class EmployeeReportDTO {

    private String employeeName;

    private String employeeEmail;

    private int numberOfLoans;

    private String duration;

    public EmployeeReportDTO(){}

    public EmployeeReportDTO(String employeeName, String employeeEmail, int numberOfLoans, String duration){
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.numberOfLoans = numberOfLoans;
        this.duration = duration;
    }

}
