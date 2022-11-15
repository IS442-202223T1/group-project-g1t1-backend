package com.is442project.cpa.dashboard.service;

import com.is442project.cpa.account.model.UserAccount;
import com.is442project.cpa.booking.model.Booking;
import com.is442project.cpa.booking.model.BookingRepository;
import com.is442project.cpa.booking.model.Booking.BookingStatus;
import com.is442project.cpa.dashboard.dto.EmployeeReportDTO;
import com.is442project.cpa.dashboard.dto.MonthlyReportDTO;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

@Component
public class DashboardService implements DashboardOps {

    private BookingRepository bookingRepository;

    public DashboardService(BookingRepository bookingRepository){
        this.bookingRepository = bookingRepository;
    }

    public List<MonthlyReportDTO> getMonthlyReport(){

        List<MonthlyReportDTO> resultsList = new ArrayList<MonthlyReportDTO>();

        Booking earliestBooking = bookingRepository.findTopByOrderByBorrowDateAsc();
        LocalDate earliestDate = earliestBooking.getBorrowDate();
        int earliestMonth = earliestDate.getMonthValue();
        int earliestYear = earliestDate.getYear();

        for (int year = earliestYear; year <= LocalDate.now().getYear(); year++){
            for (int month = earliestMonth; month <= 12; month++){
                MonthlyReportDTO monthlyReport = new MonthlyReportDTO();
                monthlyReport.setMonth(Month.of(month).toString());
                monthlyReport.setYear(Integer.toString(year));

                LocalDate start = LocalDate.of(year, month, 1);
                LocalDate end = YearMonth.of(year, month).atEndOfMonth();
            
                List<Booking> bookingsInMonth = bookingRepository.findByBorrowDateBetweenAndBookingStatusNot(start, end, BookingStatus.CANCELLED);
                monthlyReport.setNumberOfLoans(bookingsInMonth.size());

                Set<String> uniqueUsers = new HashSet<String>();
                for (Booking booking:bookingsInMonth){
                    uniqueUsers.add(booking.getBorrower().getEmail());
                }
                monthlyReport.setNumberOfBorrowers(uniqueUsers.size());

                resultsList.add(monthlyReport);
            }
            earliestMonth = 1;
        }

            return resultsList;
    }

    public List<EmployeeReportDTO> getEmployeeUsageReport(String duration, List<UserAccount> allUsers){

        LocalDate now = LocalDate.now();
        int thisMonth = now.getMonthValue();
        int thisYear = now.getYear();
        LocalDate end = LocalDate.now();
        LocalDate start = LocalDate.now();
        String period = new String();

        if(duration.equals("month")){
            start = now.withDayOfMonth(1);
            end = now.withDayOfMonth(now.getMonth().length(now.isLeapYear()));
            period = Month.of(thisMonth).toString() + " " + thisYear;
        } else if(duration.equals("biannual")){
            if(now.getMonthValue() <= 6){
                start = now.withMonth(1).withDayOfMonth(1);
                end = now.withMonth(6).withDayOfMonth(30);
                period = "Jan " + thisYear + " - Jun " + thisYear;
            } else {
                start = now.withMonth(7).withDayOfMonth(1);
                end = now.withMonth(12).withDayOfMonth(31);
                period = "Jul " + thisYear + " - Dec " + thisYear;
            } 
        } else if(duration.equals("annual")){
            start = now.with(TemporalAdjusters.firstDayOfYear());
            end = now.with(TemporalAdjusters.lastDayOfYear());
            period = thisYear + "";
        } else {
            throw new EntityNotFoundException("Invalid duration");
        }

        List<EmployeeReportDTO> resultList = new ArrayList<>();
        List<Booking> bookings = bookingRepository.findByBorrowDateBetweenAndBookingStatusNot(start, end, BookingStatus.CANCELLED);

        for (UserAccount user:allUsers){
            String email = user.getEmail();
            String name = user.getName();
            EmployeeReportDTO employeeReport = new EmployeeReportDTO(name, email, 0, period);
            resultList.add(employeeReport);
        }
   
        for (Booking booking:bookings){
            String borrowerEmail = booking.getBorrower().getEmail();
            for (EmployeeReportDTO employee:resultList){
                if (employee.getEmployeeEmail().equals(borrowerEmail)){
                    employee.setNumberOfLoans(employee.getNumberOfLoans() + 1);
                }
            }
        }

        return resultList;
    }
    
}
