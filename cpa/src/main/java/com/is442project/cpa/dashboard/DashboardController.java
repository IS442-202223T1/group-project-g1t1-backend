package com.is442project.cpa.dashboard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    @GetMapping("/getAllDates")
    public ResponseEntity getAllDates(@RequestParam("borrow_date") Date borrowDate) {
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/getAll")
    public ResponseEntity getAll(@RequestParam String userId){
        return ResponseEntity.ok("Success");
    }
}
