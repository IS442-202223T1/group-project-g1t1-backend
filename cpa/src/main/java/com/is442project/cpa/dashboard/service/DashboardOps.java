package com.is442project.cpa.dashboard.service;

import java.util.List;

import com.is442project.cpa.account.model.UserAccount;
import com.is442project.cpa.dashboard.dto.EmployeeReportDTO;
import com.is442project.cpa.dashboard.dto.MonthlyReportDTO;

public interface DashboardOps {

    public abstract List<EmployeeReportDTO> getEmployeeUsageReport(String duration, List<UserAccount> allUsers);
	
    public abstract List<MonthlyReportDTO> getMonthlyReport();

}
