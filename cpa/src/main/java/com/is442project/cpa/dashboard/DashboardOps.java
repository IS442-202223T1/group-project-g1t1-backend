package com.is442project.cpa.dashboard;

import java.util.List;

import com.is442project.cpa.account.UserAccount;

public interface DashboardOps {

    public abstract List<EmployeeReportDTO> getEmployeeUsageReport(String duration, List<UserAccount> allUsers);

	public abstract List<MonthlyReportDTO> getMonthlyReport();

}
