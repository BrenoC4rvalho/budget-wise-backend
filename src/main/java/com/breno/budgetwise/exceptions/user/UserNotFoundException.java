package com.breno.budgetwise.exceptions.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("The user not found.");
    }

}
