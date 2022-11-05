package com.is442project.cpa.booking.exception;

public class MembershipNotFoundException extends RuntimeException{

    public MembershipNotFoundException(String membershipId) {
        super("No such <" + membershipId + "> in system");
    }
}
