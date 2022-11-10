package com.is442project.cpa.account;

public interface UserOps {
    ProfileDTO userLogin(String email, String password);
    UserAccount createUser(UserCreateRequest userCreateRequest);
    UserAccount readUserByEmail(String email);
}
