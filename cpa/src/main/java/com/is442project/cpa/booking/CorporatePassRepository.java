package com.is442project.cpa.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.*;
@Repository
public interface CorporatePassRepository extends JpaRepository<CorporatePass, Long> {
    Optional<CorporatePass> findByNumber(String cardID);

    Optional<List<CorporatePass>> findByMembershipType(String membershipType);

    @Query("SELECT new com.is442project.cpa.booking.CorporatePass(c.MEMBERSHIP_TYPE, c.NUMBER, c.STATUS, c.MAX_PERSONS_ADMITTED) FROM CORPORATE_PASS c, BOOKING b where c.ID = b.CORPORATE_PASS_ID and c.MEMBERSHIP_TYPE = :membershipName and (SELECT COUNT(*) FROM BOOKING WHERE BORROW_DATE = :borrowDate) = 0")
    List<CorporatePass> getAvailablePassesForBooking(Month borrowDate, String membershipName);
}
