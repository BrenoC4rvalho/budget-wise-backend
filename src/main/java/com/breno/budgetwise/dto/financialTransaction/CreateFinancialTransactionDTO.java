package com.breno.budgetwise.dto.financialTransaction;

import com.breno.budgetwise.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateFinancialTransactionDTO {

    @NotBlank(message = "The title must not be blank.")
    private String title;

    @NotNull(message = "Transaction type must not be null.")
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @NotNull(message = "Transaction date must not be null.")
    private LocalDate transactionDate;

    @NotNull(message = "Amount must not be null.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero.")
    private BigDecimal amount;

    private UUID budgetId;

}
