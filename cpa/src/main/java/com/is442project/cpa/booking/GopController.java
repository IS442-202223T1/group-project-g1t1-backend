package com.is442project.cpa.booking;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/bookings/open")
    public List<Booking> getAllOpenBookings() {
        return gopOps.getAllOpenBookings();
    }
}
