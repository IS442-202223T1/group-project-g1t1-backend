package com.is442project.cpa.authentication;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.is442project.cpa.account.AccountService;
import com.is442project.cpa.account.UserAccount;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationUserDetailService implements UserDetailsService {
    private final AccountService adminService;
    @Override public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAccount user = adminService.readUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        // TODO: dynamically add roles based on the user's roles
        authorities.add(new SimpleGrantedAuthority("Borrower"));
        authorities.add(new SimpleGrantedAuthority("Admin"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
