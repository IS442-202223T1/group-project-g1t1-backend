package com.is442project.cpa.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    private final UserOps userOps;
    private final String API_V1_ADMIN = "/api/v1/admin";
    public final String API_V1_ADMIN_LOGIN = API_V1_ADMIN+ "/login";

    public AdminController(AdminService adminService) {
        userOps = adminService;
    }

    @GetMapping(API_V1_ADMIN_LOGIN)
    public ProfileDto login(@RequestParam("email") String email, @RequestParam("password") String password) {
        return userOps.userLogin(email, password);
    }


}
