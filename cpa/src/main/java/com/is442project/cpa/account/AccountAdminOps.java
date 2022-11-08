package com.is442project.cpa.account;

public interface AccountAdminOps {
    boolean disableEmployee(String email);
    boolean deleteEmployee(String email);
    boolean grantRole(String email, String roleName);
    boolean revokeRole(String email, String roleName);
}