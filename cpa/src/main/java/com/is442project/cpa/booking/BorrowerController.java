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

    @PostMapping("/add")
    public ResponseEntity addBooking(@RequestBody BookingDTO bookingDTO){
        return borrowerOps.bookPass(bookingDTO);
    }

}
