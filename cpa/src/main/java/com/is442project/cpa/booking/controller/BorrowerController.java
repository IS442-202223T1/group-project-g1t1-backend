package com.is442project.cpa.booking.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.is442project.cpa.booking.dto.BookerDetailsResponseDTO;
import com.is442project.cpa.booking.dto.BookingDTO;
import com.is442project.cpa.booking.dto.BookingEmailDTO;
import com.is442project.cpa.booking.dto.BookingIDDTO;
import com.is442project.cpa.booking.dto.BookingResponseDTO;
import com.is442project.cpa.booking.dto.GetBookingsDTO;
import com.is442project.cpa.booking.dto.MembershipForBorrowerResponseDTO;
import com.is442project.cpa.booking.model.Booking;
import com.is442project.cpa.booking.model.CorporatePass;
import com.is442project.cpa.booking.model.Membership;
import com.is442project.cpa.booking.service.BookingService;
import com.is442project.cpa.booking.service.BorrowerOps;

@RestController
@RequestMapping("/api/v1/borrower")
public class BorrowerController {
    
    private final BorrowerOps borrowerOps;

    public BorrowerController(BookingService bookingService) {
        this.borrowerOps = bookingService;
    }

    @GetMapping("/")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<Membership>> getMemberships() {
        List<Membership> result = borrowerOps.getAllActiveMemberships();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/available-booking")
    public ResponseEntity availableBooking(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate){
        HashMap<LocalDate, HashSet<CorporatePass>> bookingList = borrowerOps.getAvailableBooking(LocalDate.parse(startDate), LocalDate.parse(endDate));
        return ResponseEntity.ok(bookingList);
    }

    @PostMapping("/booking/create-booking")
    public ResponseEntity addBooking(@RequestBody BookingDTO bookingDto){
        try {
            List<Booking> newBookings = borrowerOps.bookPass(bookingDto);
            return ResponseEntity.ok(newBookings);
        } catch (RuntimeException e) {
            if(e.getMessage() == "Insufficient Passes") {
                List<Booking> currentBookings = borrowerOps.getBookingsByDayAndMembership(bookingDto.getDate(), bookingDto.getMembershipName());

                List<BookerDetailsResponseDTO> responseList = new ArrayList<>();
                for (Booking booking:currentBookings){
                    BookerDetailsResponseDTO responseDto = new BookerDetailsResponseDTO(booking.getBorrower().getName(), booking.getBorrower().getContactNumber(), booking.getCorporatePass().getPassID());
                    responseList.add(responseDto);
                }

                return ResponseEntity.status(HttpStatus.CONFLICT).body(responseList);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
    }

    @PostMapping("/booking/email")
    public ResponseEntity sendEmail(@RequestBody BookingEmailDTO bookingEmailDTO){
        try {
            boolean emailSent = borrowerOps.sendEmail(bookingEmailDTO);
            return ResponseEntity.ok(emailSent);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }   
    }

    @GetMapping("/membership/{membershipName}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<MembershipForBorrowerResponseDTO> getMembershipDetails(
        @PathVariable("membershipName") String membershipName) {
        Membership membership = borrowerOps.getMembershipByName(membershipName);
        MembershipForBorrowerResponseDTO membershipDTO = this.convertToMembershipForBorrowerResponseDTO(membership);
        return new ResponseEntity<>(membershipDTO, HttpStatus.OK);
    }

    @PutMapping("/cancelBooking")
    public ResponseEntity cancelBooking(@RequestBody BookingIDDTO bookingIDDTO){
        boolean cancelled = borrowerOps.cancelBooking(bookingIDDTO.getBookingID());
        if(cancelled){
            return ResponseEntity.ok(cancelled);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to cancel booking");
    }

    private MembershipForBorrowerResponseDTO convertToMembershipForBorrowerResponseDTO(Membership membership) {
        ModelMapper mapper = new ModelMapper();
        MembershipForBorrowerResponseDTO membershipForBorrowerResponseDTO = mapper.map(membership, MembershipForBorrowerResponseDTO.class);

        return membershipForBorrowerResponseDTO;
    }

    @PostMapping("/upcoming-bookings")
    public ResponseEntity upcomingBookings(@RequestBody GetBookingsDTO getBookingsDTO){
        List<BookingResponseDTO> bookingList = borrowerOps.getUpcomingBookings(getBookingsDTO.getEmail());
        return ResponseEntity.ok(bookingList);
    }

    @PostMapping("/past-bookings")
    public ResponseEntity pastBookings(@RequestBody GetBookingsDTO getBookingsDTO){
        List<BookingResponseDTO> bookingList = borrowerOps.getPastBookings(getBookingsDTO.getEmail());
        return ResponseEntity.ok(bookingList);
    }
}
