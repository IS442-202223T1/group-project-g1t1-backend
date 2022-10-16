package com.is442project.cpa.admin;

import com.is442project.cpa.admin.exception.UnauthorizedException;
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

        if(email.equalsIgnoreCase("testex")) {
            throw new UnauthorizedException("userlogin");
        }

        return new ProfileDto("test", "test", "123456", Arrays.asList("Administrator"));
    }
}
