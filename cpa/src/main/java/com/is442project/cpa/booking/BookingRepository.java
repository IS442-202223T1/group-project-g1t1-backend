package com.is442project.cpa.booking;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {

    @Query(value = "SELECT * FROM BOOKING WHERE YEAR(BORROW_DATE) = ?1 AND MONTH(BORROW_DATE) = ?2 AND EMAIL = ?3 AND (BOOKING_STATUS='CONFIRMED' OR BOOKING_STATUS='COLLECTED')", nativeQuery = true)
    List<Booking> getConfirmedAndCollectedLoansForMonthByUser(int year, int month, String email);

    @Query(value = "SELECT COUNT(*) FROM BOOKING WHERE BORROW_DATE=?1 AND EMAIL = ?2", nativeQuery = true)
    int countBookingsForDayByUser(LocalDate date, String email);

    @Query(value = "SELECT b.* FROM BOOKING b, CORPORATE_PASS c where b.CP_ID = c.ID AND BORROW_DATE = ?1 AND (BOOKING_STATUS = 'CONFIRMED' OR BOOKING_STATUS = 'COLLECTED') AND MEMBERSHIP = ?2", nativeQuery = true)
    List<Booking> getBookingsByDayAndMembership(LocalDate date, String membershipName);
}
