package com.is442project.cpa.booking.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.is442project.cpa.booking.dto.PassStatusUpdateDTO;
import com.is442project.cpa.booking.model.Booking;
import com.is442project.cpa.booking.model.CorporatePass;
import com.is442project.cpa.booking.service.BookingService;
import com.is442project.cpa.booking.service.GopOps;

@RestController
@RequestMapping("/api/v1/gop")
public class GopController {
    
    private final GopOps gopOps;

    public GopController(BookingService bookingService) {
        gopOps = bookingService;
    }

    @PatchMapping("/corporate-pass/update-pass-status")
    public ResponseEntity updatePassStatus(@RequestBody PassStatusUpdateDTO bookingOpsData) {
        // TODO: rename card to correct entity
        gopOps.updateBookingStatus(Integer.parseInt(bookingOpsData.getBookingID()), bookingOpsData.getActionToPerform());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/corporate-pass/get-all")
    public List<CorporatePass> getAllPasses() {
        return gopOps.getAllPasses();
    }

    @GetMapping("/bookings/{email}")
    public ResponseEntity bookingsByEmail(@PathVariable("email") String email){
        List<Booking> bookingList = gopOps.getBookingsByEmail(email);
        return ResponseEntity.ok(bookingList);
    }

    @GetMapping("/bookings/today")
    public ResponseEntity bookingsByDate(){
        List<Booking> bookingList = gopOps.getBookingsToday();
        return ResponseEntity.ok(bookingList);
    }

    @GetMapping(value={"/bookings-containing","/bookings-containing/{email}"})
    public ResponseEntity bookingsContainingEmail(@PathVariable Optional<String> email){
        String emailValue = "";

        if(email.isPresent()) {
            emailValue = email.get();
        }

        List<Booking> bookingList = gopOps.getBookingsContainingEmail(emailValue);
        return ResponseEntity.ok(bookingList);
    }

    @GetMapping("/bookings/")
    public ResponseEntity bookingsByEmail(){
        List<Booking> bookingList = gopOps.getBookingsByEmail("");
        return ResponseEntity.ok(bookingList);
    }
}
