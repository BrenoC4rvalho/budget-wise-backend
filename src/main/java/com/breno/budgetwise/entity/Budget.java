package com.breno.budgetwise.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

@Entity(name = "budgets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Budget date must not be null.")
    @Column(name = "budget_date")
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate budgetDate;

    @NotNull(message = "Income amount must not be null")
    @DecimalMin(value = "0.0", message = "Income amount must be greater than or equal to zero.")
    @Column(name = "income_amount")
    private BigDecimal incomeAmount;

    @NotNull(message = "Expense amount must not be null.")
    @DecimalMin(value = "0.0", message = "Expense amount must be greater than or equal to zero.")
    @Column(name = "expense_amount")
    private BigDecimal expenseAmount;

    @ManyToOne()
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "user_id")
    private UUID userId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

}
