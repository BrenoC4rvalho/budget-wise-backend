package com.breno.budget_wise.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Pattern(regexp = "\\S+", message = "The username field must not contain blank spaces.")
    @Length(min = 3, max = 20, message = "The username field between 3 and 20 characters.")
    @Column(unique = true)
    private String username;

    @Email(message = "The email field must contain a valid email address.")
    @Column(unique = true)
    private String email;

    @Length(min = 10, max = 50, message = "The password field between 10 and 50 characters.")
    private String password;

    @NotNull(message = "Date of birth not be null.")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

}
