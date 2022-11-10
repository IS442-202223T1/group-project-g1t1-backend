package com.is442project.cpa.dashboard;

import com.is442project.cpa.booking.*;
import java.util.*; 

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import javax.persistence.EntityNotFoundException;

@Component
public class DashboardService {
    private BookingRepository bookingRepository;

    public DashboardService(BookingRepository bookingRepository){
        this.bookingRepository = bookingRepository;
    }

    public MonthlyReportDTO getMonthlyReport(String year, String month){
        
        LocalDate start = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), 1);
        LocalDate end = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month) + 1, 1);

        end.minusDays(1);
            List<Booking> bookings = bookingRepository.findByBorrowDateBetween(start, end);
            Map<String, String> uniqueBookings = new HashMap<>();
            Map<String, String> uniqueBorrowers  = new HashMap<>();
            for(Booking booking : bookings){
                String borrowDate = booking.getBorrowDate().toString();
                String borrowerID = booking.getBorrower().getEmail();
                String bookingKey = borrowDate + borrowerID + booking.getCorporatePass().getMembership().getMembershipName();

                if(!(uniqueBookings.containsKey(bookingKey))){
                    uniqueBookings.put(bookingKey, "present");
                }
                if(!(uniqueBorrowers.containsKey(borrowerID))){
                    uniqueBorrowers.put(borrowerID, "present");
                }
            }

            return new MonthlyReportDTO(uniqueBookings.size(), uniqueBorrowers.size());
    }

    public EmployeeReportDTO getEmployeeUsageReport(String email, String duration){
        LocalDate end = LocalDate.now();
        LocalDate start = LocalDate.now();
        if(duration.equals("monthly")){
            start = LocalDate.of(end.getYear(), end.getMonth(), 1);
        }
        else if(duration.equals("bi-annually")){
            start = start.minusMonths(6);
        }
        else{
            start = start.minusYears(1);
        }
        List<Booking> bookings = bookingRepository.findByBorrowDateBetween(start, end);

        Map<String, String> uniqueBookings = new HashMap<>();
        for(Booking booking  : bookings){
                String borrowDate = booking.getBorrowDate().toString();
                String borrowerID = booking.getBorrower().getEmail();
                String bookingKey = borrowDate + borrowerID + booking.getCorporatePass().getMembership().getMembershipName();
            if(booking.getBorrower().getEmail().equals(email)){
                if(!(uniqueBookings.containsKey(bookingKey))){
                    uniqueBookings.put(bookingKey, booking.getBorrowDate().toString());
                }
            }
        }

        return new EmployeeReportDTO(uniqueBookings.size());
        
    }
}
