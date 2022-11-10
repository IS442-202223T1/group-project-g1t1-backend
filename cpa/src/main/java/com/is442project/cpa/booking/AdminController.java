package com.is442project.cpa.booking;

import org.modelmapper.ModelMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @GetMapping({ "/membership" })
  @ResponseStatus(code = HttpStatus.OK)
  public ResponseEntity<List<Membership>> getMemberships() {
    List<Membership> result = adminOps.getAllMemberships();
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @GetMapping({ "/membership/{membershipName}" })
  @ResponseStatus(code = HttpStatus.OK)
  public ResponseEntity<MembershipDTO> getMembershipDetails(
      @PathVariable("membershipName") String membershipName) {
    Membership membership = adminOps.getMembershipByName(membershipName);
    List<CorporatePass> passes = adminOps.getAllPassesByMembership(membership);
    MembershipDTO membershipDTO = this.convertToMembershipDTO(membership, passes);
    return new ResponseEntity<>(membershipDTO, HttpStatus.OK);
  }

  @PostMapping({ "/membership/create-membership" })
  @ResponseStatus(code = HttpStatus.CREATED)
  public ResponseEntity<Membership> createMembership(@RequestBody Membership newMembership) {
    Membership result = adminOps.createMembership(newMembership);
    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

  @PatchMapping({"/membership/update-membership/{membershipName}"})
  @ResponseStatus(code = HttpStatus.OK)
  public ResponseEntity<Membership> updateMembership(
    @PathVariable("membershipName") String membershipName,
    @RequestBody Membership updatedMembership) {
    Membership result = adminOps.updateMembership(membershipName, updatedMembership);
      return new ResponseEntity<>(result, HttpStatus.OK);
  }

  private MembershipDTO convertToMembershipDTO(Membership membership, List<CorporatePass> passes) {
    ModelMapper mapper = new ModelMapper();
    MembershipDTO membershipDTO = mapper.map(membership, MembershipDTO.class);

    membershipDTO.setCorporatePasses(passes);
    return membershipDTO;
  }
}
