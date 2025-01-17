package com.breno.budgetwise.exceptions.budget;

public class BudgetNotFoundException extends RuntimeException {

    public BudgetNotFoundException() {
        super("Budget not found.");
    }

}
