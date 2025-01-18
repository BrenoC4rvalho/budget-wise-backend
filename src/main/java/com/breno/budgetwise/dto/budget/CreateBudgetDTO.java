package com.breno.budgetwise.dto.budget;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
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
public class CreateBudgetDTO {

    @NotNull(message = "Budget date must not be null.")
    @JsonFormat(pattern = "yyyy-MM")
    private LocalDate budgetDate;

    @NotNull(message = "Income amount must not be null")
    @DecimalMin(value = "0.0", message = "Income amount must be greater than or equal to zero.")
    private BigDecimal incomeAmount;

    @NotNull(message = "Expense amount must not be null.")
    @DecimalMin(value = "0.0", message = "Expense amount must be greater than or equal to zero.")
    private BigDecimal expenseAmount;

    @NotNull(message = "User id must not be null.")
    private UUID userId;

}
