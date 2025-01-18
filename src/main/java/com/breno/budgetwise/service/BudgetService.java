package com.breno.budgetwise.service;

import com.breno.budgetwise.dto.budget.BudgetRespondeDTO;
import com.breno.budgetwise.dto.budget.CreateBudgetDTO;
import com.breno.budgetwise.entity.Budget;
import com.breno.budgetwise.exceptions.budget.BudgetDeletionException;
import com.breno.budgetwise.exceptions.budget.BudgetNotFoundException;
import com.breno.budgetwise.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BudgetService {

    @Autowired
    BudgetRepository budgetRepository;

    public BudgetRespondeDTO getById(UUID id) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(BudgetNotFoundException::new);

        return new BudgetRespondeDTO(
                budget.getId(),
                budget.getBudgetDate(),
                budget.getIncomeAmount(),
                budget.getExpenseAmount(),
                budget.getUserId()
        );
    }

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

        return new BudgetRespondeDTO(
                newBudget.getId(),
                newBudget.getBudgetDate(),
                newBudget.getIncomeAmount(),
                newBudget.getExpenseAmount(),
                newBudget.getUserId()
        );
    }

    public List<BudgetRespondeDTO> getAllByUserId(UUID userId) {

        List<Budget> budgets = budgetRepository.findAllByUserId(userId)
                .orElseThrow(BudgetNotFoundException::new);


        return budgets.stream()
                .map(budget -> new BudgetRespondeDTO(
                        budget.getId(),
                        budget.getBudgetDate(),
                        budget.getIncomeAmount(),
                        budget.getExpenseAmount(),
                        budget.getUserId()
                ))
                .collect(Collectors.toList());
    }

    public void delete(UUID id) {

        if(!budgetRepository.existsById(id)) {
            throw new BudgetNotFoundException();
        }

        budgetRepository.deleteById(id);

    }

    public void deleteAllByUserId(UUID userId) {
        try {
            budgetRepository.deleteAllByUserId(userId);
        } catch (Exception e) {
            throw new BudgetDeletionException(userId);
        }
    }

}
