package com.breno.budgetwise.entity;

import com.breno.budgetwise.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "financial_transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "The title must not be blank.")
    private String title;

    @NotNull(message = "Transaction type must not be null.")
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @NotNull(message = "Transaction date must not be null.")
    @DateTimeFormat(pattern = "yyyy-MM")
    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @NotNull(message = "Amount must not be null.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero.")
    private BigDecimal amount;

    @ManyToOne()
    @JoinColumn(name = "budget_id", insertable = false, updatable = false)
    private Budget budget;

    @Column(name = "budget_id")
    private UUID budgetId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

}
