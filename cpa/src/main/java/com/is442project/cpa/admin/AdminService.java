package com.is442project.cpa.admin;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Arrays;

import javax.persistence.EntityNotFoundException;

@Component
public class AdminService implements UserOps {

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
        userAccountRepository.save(user);
        return user;
    }

    public AdminService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public ProfileDto userLogin(String email, String password) {
        return new ProfileDto("test", "test", "123456", Arrays.asList("Administrator"));
    }
}
