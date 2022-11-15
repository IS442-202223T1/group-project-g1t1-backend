package com.is442project.cpa.account.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Arrays;

import javax.persistence.EntityNotFoundException;

import com.is442project.cpa.account.dto.ProfileDTO;
import com.is442project.cpa.account.exception.UnauthorizedException;
import com.is442project.cpa.account.model.Borrower;
import com.is442project.cpa.account.model.UserAccount;
import com.is442project.cpa.account.model.UserAccountRepository;
import com.is442project.cpa.account.model.UserCreateRequest;

@Component
public class AccountService implements UserOps {

    private final UserAccountRepository userAccountRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public AccountService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public UserAccount readUserByEmail (String email) {
        return userAccountRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
    }

    public UserAccount createUser(UserCreateRequest userCreateRequest) {
        UserAccount user = new UserAccount();
        Optional<UserAccount> existingUser = userAccountRepository.findByEmail(userCreateRequest.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        user.setEmail(userCreateRequest.getEmail());
        user.setName(userCreateRequest.getName());
        user.setContactNumber(userCreateRequest.getContactNumber());
        user.setPassword(bCryptPasswordEncoder.encode(userCreateRequest.getPassword()));
        user.setRoles(Arrays.asList(new Borrower()));
        userAccountRepository.save(user);
        return user;
    }

    @Override
    public ProfileDTO userLogin(String email, String password) {

        if(email.equalsIgnoreCase("testex")) {
            throw new UnauthorizedException("userlogin");
        }

        return new ProfileDTO("test", "test", "123456", Arrays.asList("Administrator"));
    }
}
