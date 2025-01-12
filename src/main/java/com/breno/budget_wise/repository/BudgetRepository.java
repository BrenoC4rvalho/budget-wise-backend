package com.breno.budget_wise.repository;

import com.breno.budget_wise.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BudgetRepository extends JpaRepository<Budget, UUID> {
}
