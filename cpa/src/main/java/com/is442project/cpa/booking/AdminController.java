package com.is442project.cpa.booking;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
  private final AdminOps adminOps;

  public AdminController(BookingService bookingService) {
    adminOps = bookingService;
  }

  @GetMapping({ "/membership/{membershipType}" })
  @ResponseStatus(code = HttpStatus.OK)
  public ResponseEntity<List<CorporatePass>> getMembershipTypeDetails(
    @PathVariable("membershipType") String membershipType
  ) {
    List<CorporatePass> result = adminOps.getAllPassesByMembershipType(membershipType);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
