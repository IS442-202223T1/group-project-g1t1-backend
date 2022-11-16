package com.is442project.cpa.booking.controller;

import org.modelmapper.ModelMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.is442project.cpa.booking.dto.MembershipDTO;
import com.is442project.cpa.booking.model.CorporatePass;
import com.is442project.cpa.booking.model.Membership;
import com.is442project.cpa.booking.service.AdminOps;
import com.is442project.cpa.booking.service.BookingService;

@RestController
@RequestMapping("/api/v1/booking/admin")
public class AdminController {

    private final AdminOps adminOps;

    public AdminController(BookingService bookingService) {
        adminOps = bookingService;
    }

    @GetMapping("/membership")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<Membership>> getMemberships() {
        List<Membership> result = adminOps.getAllMemberships();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/membership/{membershipName}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<MembershipDTO> getMembershipDetails(@PathVariable("membershipName") String membershipName) {
        Membership membership = adminOps.getMembershipByName(membershipName);
        List<CorporatePass> passes = adminOps.getActivePassesByMembership(membership);
        MembershipDTO membershipDTO = this.convertToMembershipDTO(membership, passes);
        return new ResponseEntity<>(membershipDTO, HttpStatus.OK);
    }

    @PostMapping("/membership/create-membership")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<MembershipDTO> createMembershipDTO(@RequestBody MembershipDTO newMembershipDTO) {
        Membership newMembership = convertToMembershipEntity(newMembershipDTO);
        newMembership.setIsActive(true);
        Membership membership = adminOps.createMembership(newMembership);

        List<CorporatePass> newPasses = newMembershipDTO.getCorporatePasses();
        newPasses = adminOps.createPasses(membership, newPasses);

        MembershipDTO result = this.convertToMembershipDTO(membership, newPasses);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/membership/update-membership/{membershipName}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<MembershipDTO> updateMembershipDTO(
        @PathVariable("membershipName") String membershipName,
        @RequestBody MembershipDTO updatedMembershipDTO) {
        List<CorporatePass> updatedPasses = updatedMembershipDTO.getCorporatePasses();
        updatedPasses = adminOps.updatePasses(membershipName, updatedPasses);

            Membership updatedMembership = convertToMembershipEntity(updatedMembershipDTO);
            updatedMembership = adminOps.updateMembership(membershipName, updatedMembership);

            MembershipDTO result = this.convertToMembershipDTO(updatedMembership, updatedPasses);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

    @PutMapping("/membership/enable-membership/{membershipName}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<MembershipDTO> enableMembership(@PathVariable("membershipName") String membershipName) {
        adminOps.enableMembership(membershipName);
        adminOps.enablePasses(membershipName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/membership/{membershipName}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<MembershipDTO> deleteMembershipDTO(@PathVariable("membershipName") String membershipName) {
        adminOps.deleteMembership(membershipName);
        adminOps.deletePasses(membershipName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping({ "/booking-by-borrower/{borrowerEmail}" })
    public ResponseEntity<Boolean> deleteBookingByBorrower(@PathVariable("borrowerEmail") String borrowerEmail) {
        adminOps.deleteBookingsByBorrower(borrowerEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private MembershipDTO convertToMembershipDTO(Membership membership, List<CorporatePass> passes) {
        ModelMapper mapper = new ModelMapper();
        MembershipDTO membershipDTO = mapper.map(membership, MembershipDTO.class);
        membershipDTO.setCorporatePasses(passes);
        return membershipDTO;
    }

    private Membership convertToMembershipEntity(MembershipDTO membershipDTO) {
        ModelMapper mapper = new ModelMapper();
        Membership membership = mapper.map(membershipDTO, Membership.class);
        return membership;
    }

}
