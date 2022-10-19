package com.is442project.cpa.booking;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
public class BorrowerController {
    private final BorrowerOps borrowerOps;

    public BorrowerController(BookingService bookingService) {
        this.borrowerOps = bookingService;
    }

    @PostMapping("/add")
    public ResponseEntity<Booking> addBooking(@RequestBody BookingDto bookingDto){
//        System.out.println(bookingDto);
//        System.out.println(bookingDto.Date);
        return borrowerOps.bookPass(bookingDto);
    }

}
