package com.is442project.cpa.account;

public interface UserOps {
    ProfileDto userLogin(String email, String password);
    UserAccount createUser(UserCreateRequest userCreateRequest);
    UserAccount readUserByEmail(String email);
}
