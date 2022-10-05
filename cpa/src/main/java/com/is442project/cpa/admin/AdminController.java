package com.is442project.cpa.admin;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    public final UserOps userOps;

    public AdminController(AdminService adminService) {
        userOps = adminService;
    }

    @GetMapping("/api/v1/user/login")
    public ProfileDto login(@RequestParam("email") String email, @RequestParam("password") String password) {
        return userOps.userLogin(email, password);
    }


}
