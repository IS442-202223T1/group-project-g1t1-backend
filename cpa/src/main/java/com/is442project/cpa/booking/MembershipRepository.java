package com.is442project.cpa.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
  Optional<Membership> findByMembershipName(String membershipName);

  List<Membership> findByIsActive(boolean isActive);
}
