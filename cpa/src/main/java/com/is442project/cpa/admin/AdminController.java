package com.is442project.cpa.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    public final UserOps userOps;

    public AdminController(AdminService adminService) {
        userOps = adminService;
    }

    @GetMapping("/test")
    public ResponseEntity test() {
        return ResponseEntity.ok("Test Success!");
    }

    @PostMapping("/create")
    public ResponseEntity createUser (@RequestBody UserCreateRequest userCreateRequest) {
        UserAccount user = userOps.createUser(userCreateRequest);
        System.out.println("User created: " + user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user_profile")
    public UserAccount getUserProfile(@RequestParam("email") String email) {
        return userOps.readUserByEmail(email);
    }

}
