package com.is442project.cpa.admin.authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.is442project.cpa.admin.UserAccount;
import com.is442project.cpa.admin.AdminService;

import java.util.Collections;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationUserDetailService implements UserDetailsService {
    private final AdminService adminService;
    @Override public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAccount user = adminService.readUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}
