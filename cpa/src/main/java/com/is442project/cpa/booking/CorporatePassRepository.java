package com.is442project.cpa.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.time.LocalDate;
import java.util.List;
@Repository
public interface CorporatePassRepository extends JpaRepository<CorporatePass, Long> {
    Optional<CorporatePass> findByPassID(String passID);

    List<CorporatePass> findByMembership(Membership membership);

    @Query(value = "SELECT * FROM CORPORATE_PASS WHERE ID NOT IN (SELECT CP_ID FROM BOOKING b, CORPORATE_PASS c WHERE b.CP_ID = C.ID AND MEMBERSHIP = ?1 AND BORROW_DATE = ?2 AND (BOOKING_STATUS='CONFIRMED' OR BOOKING_STATUS='COLLECTED')) AND STATUS!=0 AND MEMBERSHIP=?1", nativeQuery = true)
    List<CorporatePass> findAvailablePassesForDay(String membershipName, LocalDate date);
}
