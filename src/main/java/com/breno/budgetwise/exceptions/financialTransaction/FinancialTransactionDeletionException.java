package com.breno.budgetwise.exceptions.financialTransaction;

import java.util.UUID;

public class FinancialTransactionDeletionException extends RuntimeException {
    public FinancialTransactionDeletionException() {
        super("Failed to delete transactions");
    }
}
