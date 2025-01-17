package com.breno.budgetwise.repository;

import com.breno.budgetwise.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BudgetRepository extends JpaRepository<Budget, UUID> {

    Optional<Budget> findByBudgetDate(LocalDate budgetDate);
    Optional<List<Budget>> findAllByUserId(UUID userId);

}
