package com.is442project.cpa.booking;

import java.util.List;

public interface AdminOps {
  public abstract List<Membership> getAllMemberships();

  public abstract Membership getMembershipByName(String membershipName);

  public abstract List<CorporatePass> getAllPassesByMembership(Membership membership);

  public abstract Membership createMembership(Membership newMembership);

  public abstract Membership updateMembership(String membershipName, Membership updatedMembership);

  public abstract BookingConfigDTO getBookingConfiguration();

  public abstract BookingConfigDTO updateBookingConfiguration(BookingConfigDTO bookingConfigDTO);
}
