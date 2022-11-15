package com.is442project.cpa.booking.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

import com.is442project.cpa.booking.model.CorporatePass.Status;

@Repository
public interface CorporatePassRepository extends JpaRepository<CorporatePass, Long> {
    
    Optional<CorporatePass> findByPassID(String passID);

    List<CorporatePass> findByMembershipAndIsActive(Membership membership, boolean isActive);

    List<CorporatePass> findByStatusNotAndMembership(Status status, Membership membership);
}
