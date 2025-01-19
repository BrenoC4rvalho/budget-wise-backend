package com.breno.budgetwise.repository;

import com.breno.budgetwise.entity.FinancialTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, UUID> {

    Optional<List<FinancialTransaction>> findAllByBudgetId(UUID budgetId);
    void deleteAllByBudgetId(UUID budgetId);

}
