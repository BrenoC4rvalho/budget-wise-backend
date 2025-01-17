package com.breno.budgetwise.controller;

import com.breno.budgetwise.dto.budget.BudgetRespondeDTO;
import com.breno.budgetwise.dto.budget.CreateBudgetDTO;
import com.breno.budgetwise.exceptions.budget.BudgetNotFoundException;
import com.breno.budgetwise.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/budget")
public class BudgetController {

    @Autowired
    BudgetService budgetService;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody CreateBudgetDTO budget) {
        try {

            BudgetRespondeDTO result = budgetService.create(budget);
            return ResponseEntity.status(201).body(result);

        }  catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body("message: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("message: " + e.getMessage());
        }

    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getAllByUser(@PathVariable UUID userId) {
        try {

            List<BudgetRespondeDTO> result = budgetService.getAllByUserId(userId);
            return ResponseEntity.ok().body(result);

        } catch (BudgetNotFoundException e) {
            return ResponseEntity.status(404).body("message: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("message: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> destroy(@PathVariable UUID id) {
        try {

            budgetService.delete(id);
            return ResponseEntity.ok().body("message: " + "Budget delete successfully.");

        } catch (BudgetNotFoundException e) {
            return ResponseEntity.status(404).body("message: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("message: " + e.getMessage());
        }
    }


}
