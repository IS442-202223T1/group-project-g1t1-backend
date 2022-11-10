package com.is442project.cpa.booking;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity updatePassStatus(@RequestBody CardOpsData cardOpsData) {
        // TODO: rename card to correct entity
        gopOps.collectCard(Long.parseLong(cardOpsData.getCardID()));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/corporate-pass/get-all")
    public List<CorporatePass> getAllPasses() {
        return gopOps.getAllPasses();
    }
}
