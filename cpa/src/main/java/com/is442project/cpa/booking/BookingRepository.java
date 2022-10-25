package com.is442project.cpa.booking;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {
    @Query(value = "SELECT COUNT(*) FROM BOOKING WHERE YEAR(BORROW_DATE) = ?1 AND MONTH(BORROW_DATE) = ?2 AND EMAIL=?3", nativeQuery = true)
    int countForMonthByUser(int year, int month, String Email);

    @Query(value = "SELECT COUNT(*) FROM BOOKING WHERE BORROW_DATE = ?1", nativeQuery = true)
    int countPassBookingsForDate(LocalDate date);
}
