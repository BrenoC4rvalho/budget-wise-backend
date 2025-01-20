package com.breno.budgetwise.service;

import com.breno.budgetwise.dto.user.AuthUserRequestDTO;
import com.breno.budgetwise.dto.user.AuthUserResponseDTO;
import com.breno.budgetwise.dto.user.CreateUserDTO;
import com.breno.budgetwise.dto.user.UserResponseDTO;
import com.breno.budgetwise.entity.User;
import com.breno.budgetwise.exceptions.user.InvalidCredentialsException;
import com.breno.budgetwise.exceptions.user.UnderageException;
import com.breno.budgetwise.exceptions.user.UserNotFoundException;
import com.breno.budgetwise.providers.BCryptProvider;
import com.breno.budgetwise.providers.JWTProvider;
import com.breno.budgetwise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private BCryptProvider bCryptProvider;

    @Autowired
    JWTProvider jwtProvider;

    @Transactional
    public UserResponseDTO create(CreateUserDTO user) {

        userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail())
                .ifPresent(existingUser -> {
                    if(existingUser.getUsername().equals(user.getUsername())) {
                        throw new IllegalArgumentException("There is already a user with that username.");
                    }
                    if(existingUser.getEmail().equals(user.getEmail())) {
                        throw new IllegalArgumentException("There is already a user with that email.");
                    }
                });

        if(user.getDateOfBirth().plusYears(18).isAfter(LocalDate.now())) {
            throw new UnderageException();
        }

        User newUser = User.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(bCryptProvider.passwordEncode(user.getPassword()))
                .dateOfBirth(user.getDateOfBirth())
                .build();

        newUser = userRepository.save(newUser);

        System.out.println(newUser);

        return UserResponseDTO.builder()
                .id(newUser.getId())
                .username(newUser.getUsername())
                .email(newUser.getEmail())
                .dateOfBirth(newUser.getDateOfBirth())
                .build();

    }

    public UserResponseDTO getById(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .dateOfBirth(user.getDateOfBirth())
                .build();

    }

    public void delete(UUID id) {

        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }

        try {

            budgetService.deleteAllByUserId(id);
            userRepository.deleteById(id);

        } catch (Exception e) {
            throw new RuntimeException("Error occurred while deleting the user and associated budgets: " + e.getMessage());
        }

    }

    public AuthUserResponseDTO authenticateUser(AuthUserRequestDTO authUser) {

        if(authUser.email().isEmpty() && authUser.username().isEmpty()) {
            throw new InvalidCredentialsException();
        }

        User user = userRepository.findByUsernameOrEmail(authUser.username().orElse(null), authUser.email().orElse(null))
                .orElseThrow(InvalidCredentialsException::new);

        if(!bCryptProvider.passwordMatch(authUser.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return new AuthUserResponseDTO(
                jwtProvider.encodeJWT(user.getId()),
                jwtProvider.getExpirationDate().toEpochMilli()
        );

    }
}
