package com.breno.budget_wise.exceptions.user;

public class UnderageException extends RuntimeException {

    public UnderageException() {
        super("The user must be at least 18 years old.");
    }
}
