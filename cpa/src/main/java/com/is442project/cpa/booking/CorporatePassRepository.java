package com.is442project.cpa.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.is442project.cpa.booking.CorporatePass.Status;

import java.util.Optional;
import java.util.List;

@Repository
public interface CorporatePassRepository extends JpaRepository<CorporatePass, Long> {
    Optional<CorporatePass> findByPassID(String passID);

    List<CorporatePass> findByMembershipAndIsActive(Membership membership, boolean isActive);

    List<CorporatePass> findByStatusNotAndMembership(Status status, Membership membership);
}
