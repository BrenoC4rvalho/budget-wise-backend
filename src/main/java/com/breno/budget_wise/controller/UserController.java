package com.breno.budget_wise.controller;

import com.breno.budget_wise.dto.user.CreateUserDTO;
import com.breno.budget_wise.dto.user.UserResponseDTO;
import com.breno.budget_wise.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CreateUserDTO user) {
        try {

            UserResponseDTO result = userService.create(user);
            return ResponseEntity.ok().body(result);

        } catch (Exception e) {

            if(e instanceof IllegalArgumentException ) {
                return ResponseEntity.status(409).body(e.getMessage());
            }

            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }


}
