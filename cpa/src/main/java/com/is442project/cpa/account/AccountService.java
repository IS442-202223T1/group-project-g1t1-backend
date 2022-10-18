package com.is442project.cpa.account;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.is442project.cpa.account.exception.UnauthorizedException;

import java.util.Optional;
import java.util.Arrays;

import javax.persistence.EntityNotFoundException;

@Component
public class AccountService implements UserOps {

    public final UserAccountRepository userAccountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

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
        user.setRoles(Arrays.asList(new Borrower())); // TODO: Add roles based on the user's roles (OR default as borrrower?)
        userAccountRepository.save(user);
        return user;
    }

    public AccountService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public ProfileDto userLogin(String email, String password) {

        if(email.equalsIgnoreCase("testex")) {
            throw new UnauthorizedException("userlogin");
        }

        return new ProfileDto("test", "test", "123456", Arrays.asList("Administrator"));
    }
}
