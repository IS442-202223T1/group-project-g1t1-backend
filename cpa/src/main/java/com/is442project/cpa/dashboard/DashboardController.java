package com.is442project.cpa.dashboard;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService){
        this.dashboardService = dashboardService;
    }
    // monthly expects month and year specified
    @GetMapping("/monthly-report/{year}/{month}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<MonthlyReportDTO> getMonthlyReport(@PathVariable("year") String year, @PathVariable("month") String month){
        MonthlyReportDTO monthlyReport = dashboardService.getMonthlyReport(year, month);
        return new ResponseEntity<>(monthlyReport, HttpStatus.OK);
    }

    // employee expects employee name and monthly, bi annual and annual
    @GetMapping("/employee-report/{employeeEmail}/{duration}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<EmployeeReportDTO> getEmployeeUsageReport(@PathVariable("employeeEmail") String email, @PathVariable("duration") String duration){
        EmployeeReportDTO employeeReport = dashboardService.getEmployeeUsageReport(email, duration);
        return new ResponseEntity<>(employeeReport, HttpStatus.OK);
    }
}
