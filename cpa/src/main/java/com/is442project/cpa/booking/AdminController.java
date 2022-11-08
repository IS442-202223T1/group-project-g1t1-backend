package com.is442project.cpa.booking;

import org.modelmapper.ModelMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/booking/admin")
public class AdminController {
  private final AdminOps adminOps;

  public AdminController(BookingService bookingService) {
    adminOps = bookingService;
  }

  @GetMapping({ "/membership/{membershipName}" })
  @ResponseStatus(code = HttpStatus.OK)
  public ResponseEntity<MembershipDTO> getMembershipTypeDetails(
      @PathVariable("membershipName") String membershipName) {
    Membership membership = adminOps.getMembershipByName(membershipName);
    List<CorporatePass> passes = adminOps.getAllPassesByMembership(membership);
    MembershipDTO membershipDTO = this.convertToMembershipDTO(membership, passes);
    return new ResponseEntity<>(membershipDTO, HttpStatus.OK);
  }

  private MembershipDTO convertToMembershipDTO(Membership membership, List<CorporatePass> passes) {
    ModelMapper mapper = new ModelMapper();
    MembershipDTO membershipDTO = mapper.map(membership, MembershipDTO.class);

    membershipDTO.setCorporatePasses(passes);
    return membershipDTO;
  }
}
