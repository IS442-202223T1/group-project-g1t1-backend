package com.is442project.cpa.dashboard.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.is442project.cpa.account.model.UserAccount;
import com.is442project.cpa.account.service.AccountAdminService;
import com.is442project.cpa.dashboard.dto.EmployeeReportDTO;
import com.is442project.cpa.dashboard.dto.MonthlyReportDTO;
import com.is442project.cpa.dashboard.service.DashboardOps;
import com.is442project.cpa.dashboard.service.DashboardService;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    private final DashboardOps dashboardOps;

    private final AccountAdminService accountAdminService;

    public DashboardController(DashboardService dashboardService, AccountAdminService accountAdminService) {
        this.dashboardOps = dashboardService;
        this.accountAdminService = accountAdminService;
    }

    @GetMapping("/monthly-report")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity getMonthlyReport(){
        List<MonthlyReportDTO> monthlyReport = dashboardOps.getMonthlyReport();
        return new ResponseEntity<>(monthlyReport, HttpStatus.OK);
    }

    @GetMapping("/employee-report/{duration}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity getEmployeeUsageReport(@PathVariable("duration") String duration){
        List<UserAccount> allUsers = accountAdminService.getAll();
        List<EmployeeReportDTO> employeeReport = dashboardOps.getEmployeeUsageReport(duration, allUsers);
        return new ResponseEntity<>(employeeReport, HttpStatus.OK);
    }

    @GetMapping("/employee-report")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity getEmployeeUsageReport(@RequestParam("month") int month, @RequestParam("year") int year) {
        System.out.println("month: " + month + " year: " + year);
        List<UserAccount> allUsers = accountAdminService.getAll();
        List<EmployeeReportDTO> employeeReport = dashboardOps.getEmployeeUsageReport(month, year, allUsers);
        return new ResponseEntity<>(employeeReport, HttpStatus.OK);
    }
}
