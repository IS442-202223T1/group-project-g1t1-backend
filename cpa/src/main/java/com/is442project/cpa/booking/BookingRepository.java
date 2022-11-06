package com.is442project.cpa.booking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {
    // get all bookings by this user in a month
    @Query("SELECT b FROM BOOKING b WHERE b.EMAIL = ?3 AND year(b.BORROW_DATE) = ?1 AND month(b.BORROW_DATE) = ?2")
    List<Booking> getAllBookingsByUserInAMonth(int year, int month, String userName);

    List<Booking> findByEmailAndStatus(String email, String status);
}
