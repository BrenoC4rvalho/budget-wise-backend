package com.breno.budgetwise.service;

import com.breno.budgetwise.dto.budget.BudgetRespondeDTO;
import com.breno.budgetwise.dto.budget.CreateBudgetDTO;
import com.breno.budgetwise.entity.Budget;
import com.breno.budgetwise.exceptions.budget.BudgetNotFoundException;
import com.breno.budgetwise.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BudgetService {

    @Autowired
    BudgetRepository budgetRepository;

    public BudgetRespondeDTO create(CreateBudgetDTO budget) {

        budgetRepository.findByBudgetDate(budget.getBudgetDate())
                .ifPresent(existingBudget -> {
                    throw new IllegalArgumentException("There is already a budget with that date.");
                });

        Budget newBudget = Budget.builder()
                .budgetDate(budget.getBudgetDate())
                .incomeAmount(budget.getIncomeAmount())
                .expenseAmount(budget.getExpenseAmount())
                .userId(budget.getUserId())
                .build();

        newBudget = budgetRepository.save(newBudget);

        return BudgetRespondeDTO.builder()
                .id(newBudget.getId())
                .budgetDate(newBudget.getBudgetDate())
                .expenseAmount(newBudget.getExpenseAmount())
                .incomeAmount(newBudget.getIncomeAmount())
                .userId(newBudget.getUserId())
                .createdAt(newBudget.getCreatedAt())
                .build();
    }

    public void delete(UUID id) {

        if(!budgetRepository.existsById(id)) {
            throw new BudgetNotFoundException();
        }

        budgetRepository.deleteById(id);

    }

}
