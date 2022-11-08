package com.is442project.cpa.account;

import org.springframework.stereotype.Component;

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

    public boolean grantRole(String email, String roleName) {
        Optional<UserAccount> existingUser = userAccountRepository.findByEmail(email);
        if (!existingUser.isPresent()) {
            throw new IllegalArgumentException("User does not exist");
        }
        UserAccount user = existingUser.get();
        
        Role role;
        switch (roleName) {
            case "Administrator":
                role = new Administrator();
                break;
            case "Borrower":
                role = new Borrower();
                break;
            case "General Office Personnel":
                role = new GeneralOfficePersonnel();
                break;
            default:
                throw new IllegalArgumentException("Invalid role");
        }

        user.addRole(role);
        userAccountRepository.save(user);
        return true;
    }

    public boolean revokeRole(String email, String roleName) {
        Optional<UserAccount> existingUser = userAccountRepository.findByEmail(email);
        if (!existingUser.isPresent()) {
            throw new IllegalArgumentException("User does not exist");
        }
        UserAccount user = existingUser.get();
        
        Role role;
        switch (roleName) {
            case "Administrator":
                role = new Administrator();
                break;
            case "Borrower":
                role = new Borrower();
                break;
            case "General Office Personnel":
                role = new GeneralOfficePersonnel();
                break;
            default:
                throw new IllegalArgumentException("Invalid role");
        }

        user.removeRole(role);
        userAccountRepository.save(user);
        return true;
    }
}