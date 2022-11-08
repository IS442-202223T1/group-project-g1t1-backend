package com.is442project.cpa.booking;

import java.util.List;

public interface AdminOps {
  public abstract Membership getMembershipByName(String membershipName);

  public abstract List<CorporatePass> getAllPassesByMembership(Membership membership);
}
