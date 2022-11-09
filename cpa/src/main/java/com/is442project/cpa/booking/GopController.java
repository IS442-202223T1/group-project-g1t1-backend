package com.is442project.cpa.booking;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/gopOps")
public class GopController {
    private final GopOps gopOps;

    public GopController(BookingService bookingService){
        gopOps = bookingService;
    }

    @PutMapping("collectCard")
    public ResponseEntity collectCard(@RequestBody BookingIDDTO bookingIDDTO){
        gopOps.collectCard(bookingIDDTO.getBookingID());
        return ResponseEntity.ok().build();
    }

    @PutMapping("returnCard")
    public ResponseEntity returnCard(@RequestBody BookingIDDTO bookingIDDTO){
        gopOps.returnCard(bookingIDDTO.getBookingID());
        return ResponseEntity.ok().build();
    }

    @PutMapping("markLost")
    public ResponseEntity markLost(@RequestBody BookingIDDTO bookingIDDTO){
        gopOps.markLost(bookingIDDTO.getBookingID());
        return ResponseEntity.ok().build();
    }

    @GetMapping("getAllPasses")
    public List<CorporatePass> getAllPasses(){
        return gopOps.getAllPasses();
    }

    @GetMapping("getAllBookings")
    public List<Booking> getAllBookings(){
        return gopOps.getAllBookings();
    }
}
