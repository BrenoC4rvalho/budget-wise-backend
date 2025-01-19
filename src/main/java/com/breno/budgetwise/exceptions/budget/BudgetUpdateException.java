package com.breno.budgetwise.exceptions.budget;

import java.util.UUID;

public class BudgetUpdateException extends RuntimeException {
    public BudgetUpdateException() {
        super("Failed to update budget.");
    }
}
