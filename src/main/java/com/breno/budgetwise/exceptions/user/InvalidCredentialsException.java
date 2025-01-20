package com.breno.budgetwise.exceptions.user;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Username or password incorrect.");
    }
}
