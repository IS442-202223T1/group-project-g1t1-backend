package com.is442project.cpa.account.service;

import java.util.List;

import com.is442project.cpa.account.model.UserAccount;

public interface AccountAdminOps {
    boolean disableEmployee(String email);

    boolean deleteEmployee(String email);

    boolean enableEmployee(String email);

    boolean grantRole(String email, String roleName);

    boolean revokeRole(String email, String roleName);

    boolean updateRoles(String email, List<String> roleNames);

    List<UserAccount> getAll();
    
}
