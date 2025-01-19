package com.breno.budgetwise.controller;

import com.breno.budgetwise.dto.user.CreateUserDTO;
import com.breno.budgetwise.dto.user.UserResponseDTO;
import com.breno.budgetwise.entity.User;
import com.breno.budgetwise.exceptions.budget.BudgetDeletionException;
import com.breno.budgetwise.exceptions.budget.BudgetNotFoundException;
import com.breno.budgetwise.exceptions.financialTransaction.FinancialTransactionDeletionException;
import com.breno.budgetwise.exceptions.user.UnderageException;
import com.breno.budgetwise.exceptions.user.UserNotFoundException;
import com.breno.budgetwise.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody CreateUserDTO user) {
        try {

            UserResponseDTO result = userService.create(user);
            return ResponseEntity.status(201).body(result);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body("message: " + e.getMessage());
        } catch ( UnderageException e) {
            return ResponseEntity.badRequest().body("message: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("message" + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable UUID id) {
        try {

            UserResponseDTO result = userService.getById(id);
            return ResponseEntity.ok().body(result);

        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body("message: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("message: " + e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> destroy(@PathVariable UUID id) {
        try {

            userService.delete(id);
            return ResponseEntity.ok().body("User deleted successfully.");

        } catch (UserNotFoundException | BudgetNotFoundException  e) {
            return ResponseEntity.status(404).body("message: " + e.getMessage());
        } catch (BudgetDeletionException | FinancialTransactionDeletionException   e) {
            return ResponseEntity.status(400).body("message: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("message: " + e.getMessage());
        }
    }


}
