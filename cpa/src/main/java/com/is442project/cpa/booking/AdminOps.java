package com.is442project.cpa.booking;

import java.util.List;

public interface AdminOps {
  public abstract List<CorporatePass> getAllPassesByMembershipType(String membershipType);
}
