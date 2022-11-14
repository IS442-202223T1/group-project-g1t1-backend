package com.is442project.cpa.account;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AccountAdminService implements AccountAdminOps {

    private final UserAccountRepository userAccountRepository;

    public AccountAdminService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public boolean disableEmployee(String email) {
        Optional<UserAccount> existingUser = userAccountRepository.findByEmail(email);
        if (!existingUser.isPresent()) {
            throw new IllegalArgumentException("User does not exist");
        }
        UserAccount user = existingUser.get();
        user.setIsActive(false);
        userAccountRepository.save(user);
        return true;
    }

    public boolean deleteEmployee(String email) {
        Optional<UserAccount> existingUser = userAccountRepository.findByEmail(email);
        if (!existingUser.isPresent()) {
            throw new IllegalArgumentException("User does not exist");
        }
        userAccountRepository.delete(existingUser.get());
        return true;
    }

    public boolean enableEmployee(String email) {
        Optional<UserAccount> existingUser = userAccountRepository.findByEmail(email);
        if (!existingUser.isPresent()) {
            throw new IllegalArgumentException("User does not exist");
        }
        UserAccount user = existingUser.get();
        user.setIsActive(true);
        userAccountRepository.save(user);
        return true;
    }

    public boolean grantRole(String email, String roleName) {
        Optional<UserAccount> existingUser = userAccountRepository.findByEmail(email);
        if (!existingUser.isPresent()) {
            throw new IllegalArgumentException("User does not exist");
        }
        UserAccount user = existingUser.get();
        
        Role role;
        switch (roleName) {
            case "admin":
                role = new Administrator();
                break;
            case "borrower":
                role = new Borrower();
                break;
            case "gop":
                role = new GeneralOfficePersonnel();
                break;
            default:
                throw new IllegalArgumentException("Invalid role");
        }

        user.addRole(role);
        userAccountRepository.save(user);
        return true;
    }

    public List<UserAccount> getAll (){
        List<UserAccount> allUsers = userAccountRepository.findAll();
        return allUsers;
    }

    public boolean revokeRole(String email, String roleName) {
        Optional<UserAccount> existingUser = userAccountRepository.findByEmail(email);
        if (!existingUser.isPresent()) {
            throw new IllegalArgumentException("User does not exist");
        }
        UserAccount user = existingUser.get();
        
        Role role;
        switch (roleName) {
            case "admin":
                role = new Administrator();
                break;
            case "borrower":
                role = new Borrower();
                break;
            case "gop":
                role = new GeneralOfficePersonnel();
                break;
            default:
                throw new IllegalArgumentException("Invalid role");
        }

        user.removeRole(role);
        userAccountRepository.save(user);
        return true;
    }

    public boolean updateRoles(String email, List<String> roleNames) {
        Optional<UserAccount> existingUser = userAccountRepository.findByEmail(email);
        if (!existingUser.isPresent()) {
            throw new IllegalArgumentException("User does not exist");
        }
        UserAccount user = existingUser.get();
        user.clearRoles();
        for (String roleName : roleNames) {
            Role role;
            switch (roleName) {
                case "admin":
                    role = new Administrator();
                    break;
                case "borrower":
                    role = new Borrower();
                    break;
                case "gop":
                    role = new GeneralOfficePersonnel();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid role");
            }
            user.addRole(role);
        }
        userAccountRepository.save(user);
        return true;
    }
}
