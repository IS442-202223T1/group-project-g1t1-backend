package com.is442project.cpa.booking;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {
    // get all bookings by this user in a month
    // @Query("SELECT b FROM BOOKING b WHERE b.EMAIL = ?3 AND year(b.BORROW_DATE) = ?1 AND month(b.BORROW_DATE) = ?2")
    // List<Booking> getAllBookingsByUserInAMonth(int year, int month, String userName);
    // SELECT COUNT(*) FROM (SELECT DISTINCT BORROW_DATE, CORPORATE_PASS_ID FROM BOOKING WHERE YEAR(BORROW_DATE) = ?1 AND MONTH(BORROW_DATE) = ?2 AND EMAIL = ?3"
    @Query(value = "SELECT * FROM BOOKING b WHERE b.EMAIL = ?1 AND b.STATUS = ?2", nativeQuery = true)
    List<Booking> findByEmailAndStatus(String email, String status);

    List<Booking> findByStatus(String status);

    @Query(value = "SELECT * FROM BOOKING b WHERE b.CORPORATE_PASS_ID = ?1 AND b.BORROW_DATE<=?2", nativeQuery = true)
    List<Booking> findBookingsWithCorporatePassIDBeforeDate(Long corporatePassID, LocalDate date);
}
