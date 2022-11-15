package com.is442project.cpa.account.service;

import com.is442project.cpa.account.dto.ProfileDTO;
import com.is442project.cpa.account.model.UserAccount;
import com.is442project.cpa.account.model.UserCreateRequest;

public interface UserOps {
    ProfileDTO userLogin(String email, String password);

    UserAccount createUser(UserCreateRequest userCreateRequest);
    
    UserAccount readUserByEmail(String email);
}
