package com.is442project.cpa.dashboard;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.is442project.cpa.account.AccountAdminService;
import com.is442project.cpa.account.UserAccount;

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
}
