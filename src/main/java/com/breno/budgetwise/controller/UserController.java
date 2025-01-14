package com.breno.budgetwise.controller;

import com.breno.budgetwise.dto.user.CreateUserDTO;
import com.breno.budgetwise.dto.user.UserResponseDTO;
import com.breno.budgetwise.entity.User;
import com.breno.budgetwise.exceptions.user.UserNotFoundException;
import com.breno.budgetwise.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody CreateUserDTO user) {
        try {

            UserResponseDTO result = userService.create(user);
            return ResponseEntity.status(201).body(result);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable UUID id) {
        try {

            UserResponseDTO user = userService.getUserById(id);
            return ResponseEntity.ok().body(user);

        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> destroy(@PathVariable UUID id) {
        try {

            userService.delete(id);
            return ResponseEntity.ok().body("User deleted successfully.");

        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
