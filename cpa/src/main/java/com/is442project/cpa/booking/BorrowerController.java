package com.is442project.cpa.booking;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/borrower")
public class BorrowerController {
    private final BorrowerOps borrowerOps;

    public BorrowerController(BookingService bookingService) {
        this.borrowerOps = bookingService;
    }

    @PostMapping("/booking/create-booking")
    public ResponseEntity createBooking(@RequestBody BookingDTO bookingDTO){
        return borrowerOps.bookPass(bookingDTO);
    }

}
