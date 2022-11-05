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
    public ResponseEntity collectCard(@RequestBody CardOpsData cardOpsData){
        gopOps.collectCard(Long.parseLong(cardOpsData.getCardID()));
        return ResponseEntity.ok().build();
    }

    @PutMapping("returnCard")
    public ResponseEntity returnCard(@RequestBody CardOpsData cardOpsData){
        gopOps.returnCard(Long.parseLong(cardOpsData.getCardID()));
        return ResponseEntity.ok().build();
    }

    @PutMapping("markLost")
    public ResponseEntity markLost(@RequestBody CardOpsData cardOpsData){
        gopOps.markLost(Long.parseLong(cardOpsData.getCardID()));
        return ResponseEntity.ok().build();
    }

    @GetMapping("getAllPasses")
    public List<CorporatePass> getAllPasses(){
        return gopOps.getAllPasses();
    }
}
