package com.breno.budgetwise.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {

    @NotBlank
    @Pattern(regexp = "\\S+", message = "The username field must not contain blank spaces.")
    @Length(min = 3, max = 20, message = "The username field between 3 and 20 characters.")
    private String username;

    @Email(message = "The email field must contain a valid email address.")
    private String email;

    @Length(min = 10, max = 50, message = "The password field between 10 and 50 characters.")
    private String password;

    @NotNull(message = "Date of birth not be null.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

}
