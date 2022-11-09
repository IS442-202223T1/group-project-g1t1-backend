package com.is442project.cpa.booking;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking")
public class BorrowerController {
    private final BorrowerOps borrowerOps;

    public BorrowerController(BookingService bookingService) {
        this.borrowerOps = bookingService;
    }

    @GetMapping("viewAll")
    public List<Membership> viewAllAttractions(){
        return borrowerOps.getAllAttractions();
    }

    @GetMapping("/availableBooking/{email}")
    public List<Booking> getAvailableBookingsByUser(@PathVariable("email") String email){
        return borrowerOps.getAvailableBookingByUser(email);
    }

    @PostMapping("/add")
    public ResponseEntity addBooking(@RequestBody BookingDTO bookingDTO){
        return borrowerOps.bookPass(bookingDTO);
    }

    @PutMapping("cancelBooking")
    public ResponseEntity cancelBooking(@RequestBody BookingIDDTO bookingIDDTO){
        return ResponseEntity.ok(borrowerOps.cancelBooking(bookingIDDTO));
    }

}
