package com.is442project.cpa.booking;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
      List<Membership> result = borrowerOps.getAllMemberships();
      return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PostMapping("/booking/create-booking")
    public ResponseEntity addBooking(@RequestBody BookingDTO bookingDto){
        try {
            boolean newBookings = borrowerOps.bookPass(bookingDto);
            return ResponseEntity.ok(newBookings);
        } catch (RuntimeException e) {
            if(e.getMessage() == "Insufficient Passes") {
                List<Booking> currentBookings = borrowerOps.getBookingsByDayAndMembership(bookingDto.getDate(), bookingDto.getMembershipName());

                List<BookingDetailsResponseDTO> responseList = new ArrayList<>();
                for (Booking booking:currentBookings){
                    BookingDetailsResponseDTO responseDto = new BookingDetailsResponseDTO(booking.getBorrower().getName(), booking.getBorrower().getContactNumber(), booking.getCorporatePass().getPassID());
                    responseList.add(responseDto);
                }

                return ResponseEntity.status(HttpStatus.CONFLICT).body(responseList);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
            
        }
    }
}
