package com.breno.budgetwise.controller;

import com.breno.budgetwise.dto.budget.CreateBudgetDTO;
import com.breno.budgetwise.dto.financialTransaction.CreateFinancialTransactionDTO;
import com.breno.budgetwise.dto.financialTransaction.FinancialTransactionResponseDTO;
import com.breno.budgetwise.exceptions.financialTransaction.FinancialTransactionNotFoundException;
import com.breno.budgetwise.service.FinancialTransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transaction")
public class FinancialTransactionController {

    @Autowired
    FinancialTransactionService financialTransactionService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getFinancialTransaction(@PathVariable UUID id) {
        try {

            FinancialTransactionResponseDTO result = financialTransactionService.getById(id);
            return ResponseEntity.ok().body(result);

        } catch (FinancialTransactionNotFoundException e) {
            return ResponseEntity.status(404).body("message: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("message: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody CreateFinancialTransactionDTO financialTransaction) {
        try {

            FinancialTransactionResponseDTO result = financialTransactionService.create(financialTransaction);
            return ResponseEntity.status(201).body(result);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("message: " + e.getMessage());
        }
    }

    @GetMapping("/budget/{budgetId}")
    public ResponseEntity<Object> getAllFinancialTransactionByBudget(@PathVariable UUID budgetId) {
        try {

            List<FinancialTransactionResponseDTO> result = financialTransactionService.getAllFinancialTransactionByBudget(budgetId);
            return ResponseEntity.ok().body(result);

        } catch (FinancialTransactionNotFoundException e) {
            return ResponseEntity.status(404).body("message: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("message: " + e.getMessage());
        }
    }

}
