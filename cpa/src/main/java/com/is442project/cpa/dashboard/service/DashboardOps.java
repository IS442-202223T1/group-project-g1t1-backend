package com.is442project.cpa.dashboard.service;

import java.util.List;

import com.is442project.cpa.account.model.UserAccount;
import com.is442project.cpa.dashboard.dto.EmployeeReportDTO;
import com.is442project.cpa.dashboard.dto.MonthlyReportDTO;
import com.is442project.cpa.dashboard.dto.MembershipReportDTO;

public interface DashboardOps {

    public abstract List<EmployeeReportDTO> getEmployeeUsageReport(String duration, List<UserAccount> allUsers);

    public abstract List<EmployeeReportDTO> getEmployeeUsageReport(int month, int year, List<UserAccount> allUsers);

    public abstract List<EmployeeReportDTO> getEmployeeUsageReport(int year, List<UserAccount> allUsers);

    public abstract List<EmployeeReportDTO> getEmployeeUsageReport(int startMonth, int startYear, int endMonth, int endYear, List<UserAccount> allUsers);

    public abstract List<MonthlyReportDTO> getMonthlyReport(int year);

    public abstract List<MembershipReportDTO> getMembershipReport(String membershipName);
}
