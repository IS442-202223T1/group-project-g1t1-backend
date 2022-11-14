package com.is442project.cpa.dashboard;

import com.is442project.cpa.account.UserAccount;
import com.is442project.cpa.booking.*;
import com.is442project.cpa.booking.Booking.BookingStatus;

import java.util.*; 

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

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

        LocalDate end = LocalDate.now();
        LocalDate start = LocalDate.now();
        if(duration.equals("monthly")){
            start = start.minusMonths(1);
        } else if(duration.equals("biannual")){
            start = start.minusMonths(6);
        } else if(duration.equals("annual")){
            start = start.minusYears(1);
        } else {
            throw new EntityNotFoundException("Invalid duration");
        }

        List<EmployeeReportDTO> resultList = new ArrayList<>();
        List<Booking> bookings = bookingRepository.findByBorrowDateBetweenAndBookingStatusNot(start, end, BookingStatus.CANCELLED);

        for (UserAccount user:allUsers){
            String email = user.getEmail();
            String name = user.getName();
            EmployeeReportDTO employeeReport = new EmployeeReportDTO(name, email, 0);
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
