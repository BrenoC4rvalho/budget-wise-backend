package com.breno.budgetwise.exceptions.financialTransaction;

public class DateMismatchException extends RuntimeException {
    public DateMismatchException() {
        super("The dates are not in the same month and year");
    }
}
