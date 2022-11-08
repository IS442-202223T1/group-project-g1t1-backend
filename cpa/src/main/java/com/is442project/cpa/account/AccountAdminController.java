package com.is442project.cpa.account;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account/admin")
public class AccountAdminController {
    private final AccountAdminOps accountAdminOps;

    public AccountAdminController(AccountAdminService accountAdminService) {
        accountAdminOps = accountAdminService;
    }

    @GetMapping("/test")
    public ResponseEntity test() {
        return ResponseEntity.ok("Test Success!");
    }

    @PutMapping("/disable_employee/{email}")
    public ResponseEntity disableEmployee (@PathVariable String email) {
        try {
            accountAdminOps.disableEmployee(email);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete_employee/{email}")
    public ResponseEntity deleteEmployee (@PathVariable String email) {
        try {
            accountAdminOps.deleteEmployee(email);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/grant_role/{email}/{roleName}")
    public ResponseEntity grantRole (@PathVariable String email, @PathVariable String roleName) {
        try {
            accountAdminOps.grantRole(email, roleName);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/revoke_role/{email}/{roleName}")
    public ResponseEntity revokeRole (@PathVariable String email, @PathVariable String roleName) {
        try {
            accountAdminOps.revokeRole(email, roleName);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}