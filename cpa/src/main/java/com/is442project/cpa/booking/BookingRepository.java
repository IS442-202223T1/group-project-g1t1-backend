package com.is442project.cpa.booking;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {

    List<Booking> findByBorrowerEmail(String email);

    List<Booking> findByBorrowDate(LocalDate date);

    List<Booking> findByBorrowDateAndBorrowerEmailAndBookingStatusNot(LocalDate date,String email, Booking.BookingStatus bookingStatus);

    List<Booking> findByBorrowDateAfterAndBorrowerEmailAndBookingStatusNot(LocalDate date,String email, Booking.BookingStatus bookingStatus);

    List<Booking> findByBorrowDateBetween(LocalDate start, LocalDate end);

    List<Booking> findByBorrowDateAfter(LocalDate now);
}
