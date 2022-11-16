package com.is442project.cpa.booking.service;

import java.util.List;

import com.is442project.cpa.booking.model.CorporatePass;
import com.is442project.cpa.booking.model.Membership;

public interface AdminOps {

    public abstract List<Membership> getAllMemberships();

    public abstract Membership getMembershipByName(String membershipName);

    public abstract List<CorporatePass> getActivePassesByMembership(Membership membership);

    public abstract Membership createMembership(Membership newMembership);

    public abstract Membership updateMembership(String membershipName, Membership updatedMembership);

    public abstract void enableMembership(String membershipName);

    public abstract void deleteMembership(String membershipName);

    public abstract List<CorporatePass> createPasses(Membership membership, List<CorporatePass> updatePasses);

    public abstract List<CorporatePass> updatePasses(String membershipName, List<CorporatePass> updatePasses);

    public abstract void enablePasses(String membershipName);

    public abstract void deletePasses(String membershipName);

    public abstract void deleteBookingsByBorrower(String email);
  
}
