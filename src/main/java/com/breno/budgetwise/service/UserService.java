package com.breno.budgetwise.service;

import com.breno.budgetwise.dto.user.CreateUserDTO;
import com.breno.budgetwise.dto.user.UserResponseDTO;
import com.breno.budgetwise.entity.User;
import com.breno.budgetwise.exceptions.user.UnderageException;
import com.breno.budgetwise.exceptions.user.UserNotFoundException;
import com.breno.budgetwise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO create(CreateUserDTO user) {

        Optional<User> existUser = userRepository.findByUsernameOrEmail(user  .getUsername(), user.getEmail());

        if(existUser.isPresent() && existUser.get().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("There is already a user with that username.");
        }

        if(existUser.isPresent() && existUser.get().getEmail().equals(user.getEmail())) {
            throw new IllegalArgumentException("There is already a user with that email.");
        }

        if(user.getDateOfBirth().plusYears(18).isBefore(LocalDate.now())) {
            throw new UnderageException();
        }

        User newUser = userRepository.save(
                User.builder()
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .dateOfBirth(user.getDateOfBirth())
                        .build()
        );

        return UserResponseDTO.builder()
                .id(newUser.getId())
                .username(newUser.getUsername())
                .email(newUser.getEmail())
                .dateOfBirth(newUser.getDateOfBirth())
                .createdAt(newUser.getCreatedAt())
                .build();

    }

    public void delete(UUID id) {

        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }

        userRepository.deleteById(id);

    }
}
