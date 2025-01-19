package com.breno.budgetwise.service;

import com.breno.budgetwise.entity.Budget;
import com.breno.budgetwise.enums.TransactionType;
import com.breno.budgetwise.exceptions.budget.BudgetNotFoundException;
import com.breno.budgetwise.exceptions.budget.BudgetUpdateException;
import com.breno.budgetwise.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransactionHelperService {

    @Autowired
    private BudgetRepository budgetRepository;

    public void updateBudgetAmount(UUID id, BigDecimal amount, TransactionType type) {
        if(type.equals(TransactionType.EXPENSE)) {
            this.updateBudgetExpenseAmount(id, amount);
        } else if(type.equals(TransactionType.INCOME)) {
            this.updateBudgetIncomeAmount(id, amount);
        }
    }

    public void updateBudgetIncomeAmount(UUID id, BigDecimal amount) {
        try {
            Budget budget = budgetRepository.findById(id)
                    .orElseThrow(BudgetNotFoundException::new);

            budget.setIncomeAmount(budget.getIncomeAmount().add(amount));
            budgetRepository.save(budget);

        } catch (Exception e) {
            throw new BudgetUpdateException();
        }
    }

    public void updateBudgetExpenseAmount(UUID id, BigDecimal amount) {
        try {
            Budget budget = budgetRepository.findById(id)
                    .orElseThrow(BudgetNotFoundException::new);

            budget.setExpenseAmount(budget.getExpenseAmount().add(amount));
            budgetRepository.save(budget);

        } catch (Exception e) {
            throw new BudgetUpdateException();
        }
    }

    public boolean dateMatch(UUID budgedId, LocalDate date) {
        Budget budget = budgetRepository.findById(budgedId)
                .orElseThrow(BudgetNotFoundException::new);

        return budget.getBudgetDate().getYear() == date.getYear() &&
                budget.getBudgetDate().getMonth() == date.getMonth();
    }

}
