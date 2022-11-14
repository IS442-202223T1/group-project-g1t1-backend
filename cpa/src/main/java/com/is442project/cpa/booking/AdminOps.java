package com.is442project.cpa.booking;

import java.util.List;

public interface AdminOps {
  public abstract List<Membership> getAllMemberships();

  public abstract Membership getMembershipByName(String membershipName);

  public abstract List<CorporatePass> getActivePassesByMembership(Membership membership);

  public abstract Membership createMembership(Membership newMembership);

  public abstract Membership updateMembership(String membershipName, Membership updatedMembership);

  public abstract List<CorporatePass> updatePasses(String membershipName, List<CorporatePass> updatePasses);
}
