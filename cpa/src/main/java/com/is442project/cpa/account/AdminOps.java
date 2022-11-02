package com.is442project.cpa.account;

public interface AdminOps {
    boolean disableEmployee(String email);
    boolean grantRole(String email, String roleName);
    boolean revokeRole(String email, String roleName);
}
