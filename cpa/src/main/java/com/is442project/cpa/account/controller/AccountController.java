package com.is442project.cpa.account.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.is442project.cpa.account.model.UserAccount;
import com.is442project.cpa.account.model.UserCreateRequest;
import com.is442project.cpa.account.service.AccountService;
import com.is442project.cpa.account.service.UserOps;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    private final UserOps userOps;

    public AccountController(AccountService accountService) {
        userOps = accountService;
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

    @GetMapping("/me")
    public UserAccount getAuthenticatedUserProfile() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userOps.readUserByEmail(email);
    }
    @GetMapping("/user_profile")
    public UserAccount getUserProfile(@RequestParam("email") String email) {
        return userOps.readUserByEmail(email);
    }

}
