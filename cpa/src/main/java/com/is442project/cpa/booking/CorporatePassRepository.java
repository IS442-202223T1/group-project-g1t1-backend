package com.is442project.cpa.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CorporatePassRepository extends JpaRepository<CorporatePass, Long> {
    Optional<CorporatePass> findByNumber(String cardID);
}
