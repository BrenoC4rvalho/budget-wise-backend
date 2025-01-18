package com.breno.budgetwise.exceptions.financialTransaction;

public class FinancialTransactionNotFoundException extends RuntimeException {

    public FinancialTransactionNotFoundException() {
        super("Financial Transaction not found.");
    }

}
