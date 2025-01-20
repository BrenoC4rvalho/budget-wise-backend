package com.breno.budgetwise.controller;

import com.breno.budgetwise.dto.user.AuthUserRequestDTO;
import com.breno.budgetwise.dto.user.AuthUserResponseDTO;
import com.breno.budgetwise.dto.user.CreateUserDTO;
import com.breno.budgetwise.dto.user.UserResponseDTO;
import com.breno.budgetwise.entity.User;
import com.breno.budgetwise.exceptions.budget.BudgetDeletionException;
import com.breno.budgetwise.exceptions.budget.BudgetNotFoundException;
import com.breno.budgetwise.exceptions.financialTransaction.FinancialTransactionDeletionException;
import com.breno.budgetwise.exceptions.user.InvalidCredentialsException;
import com.breno.budgetwise.exceptions.user.UnderageException;
import com.breno.budgetwise.exceptions.user.UserNotFoundException;
import com.breno.budgetwise.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestBody AuthUserRequestDTO authUser) {
        try {

            AuthUserResponseDTO result = userService.authenticateUser(authUser);
            return ResponseEntity.ok().body(result);

        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("message: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("message:" + e.getMessage());
        }
    }

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

    @GetMapping
    public ResponseEntity<Object> getUser(HttpServletRequest request) {
        try {
            UUID userId = UUID.fromString(request.getAttribute("user_id").toString());

            UserResponseDTO result = userService.getById(userId);
            return ResponseEntity.ok().body(result);

        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body("message: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("message: " + e.getMessage());
        }
    }


    @DeleteMapping()
    public ResponseEntity<Object> destroy(HttpServletRequest request) {
        try {
            UUID userId = UUID.fromString(request.getAttribute("user_id").toString());

            userService.delete(userId);
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
