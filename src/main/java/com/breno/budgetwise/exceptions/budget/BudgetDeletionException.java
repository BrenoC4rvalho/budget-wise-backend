package com.breno.budgetwise.exceptions.budget;

import java.util.UUID;

public class BudgetDeletionException extends RuntimeException {
    public BudgetDeletionException(UUID userId) {
        super("Failed to delete budgets for user ID " + userId);
    }
}
