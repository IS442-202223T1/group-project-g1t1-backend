package com.is442project.cpa.account;

import java.util.*;

public interface AccountAdminOps {
    boolean disableEmployee(String email);
    boolean deleteEmployee(String email);
    boolean grantRole(String email, String roleName);
    boolean revokeRole(String email, String roleName);
    List<UserAccount> getAllByRole();
}
