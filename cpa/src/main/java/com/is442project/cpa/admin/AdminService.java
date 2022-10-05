package com.is442project.cpa.admin;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AdminService implements UserOps {

    public final UserAccountRepository userAccountRepository;

    public AdminService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override

    public ProfileDto userLogin(String email, String password) {
        return new ProfileDto("test", "test", "123456", Arrays.asList("Administrator"));
    }
}
