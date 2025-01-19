package com.breno.budgetwise.dto.financialTransaction;

import com.breno.budgetwise.enums.TransactionType;
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
public class FinancialTransactionResponseDTO {

    private UUID id;
    private String title;
    private TransactionType type;
    private LocalDate transactionDate;
    private BigDecimal amount;


}
