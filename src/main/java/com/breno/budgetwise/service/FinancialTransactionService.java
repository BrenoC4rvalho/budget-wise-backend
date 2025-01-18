package com.breno.budgetwise.service;

import com.breno.budgetwise.dto.financialTransaction.CreateFinancialTransactionDTO;
import com.breno.budgetwise.dto.financialTransaction.FinancialTransactionResponseDTO;
import com.breno.budgetwise.entity.FinancialTransaction;
import com.breno.budgetwise.exceptions.financialTransaction.FinancialTransactionNotFoundException;
import com.breno.budgetwise.repository.FinancialTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FinancialTransactionService {

    @Autowired
    FinancialTransactionRepository financialTransactionRepository;

    public FinancialTransactionResponseDTO getById(UUID id) {
        FinancialTransaction financialTransaction = financialTransactionRepository.findById(id)
                .orElseThrow(FinancialTransactionNotFoundException::new);

        return new FinancialTransactionResponseDTO(
                financialTransaction.getId(),
                financialTransaction.getTitle(),
                financialTransaction.getType(),
                financialTransaction.getTransactionDate(),
                financialTransaction.getAmount()
        );
    }

    public FinancialTransactionResponseDTO create(CreateFinancialTransactionDTO financialTransaction) {

        FinancialTransaction newFinancialTransaction = FinancialTransaction.builder()
                .title(financialTransaction.getTitle())
                .type(financialTransaction.getType())
                .amount(financialTransaction.getAmount())
                .transactionDate(financialTransaction.getTransactionDate())
                .budgetId(financialTransaction.getBudgetId())
                .build();

        newFinancialTransaction = financialTransactionRepository.save(newFinancialTransaction);

        return new FinancialTransactionResponseDTO(
                newFinancialTransaction.getId(),
                newFinancialTransaction.getTitle(),
                newFinancialTransaction.getType(),
                newFinancialTransaction.getTransactionDate(),
                newFinancialTransaction.getAmount()
        );
    }

    public List<FinancialTransactionResponseDTO> getAllFinancialTransactionByBudget(UUID budgetId) {

        List<FinancialTransaction> financialTransactions = financialTransactionRepository.findAllByBudgetId(budgetId)
                .orElseThrow(FinancialTransactionNotFoundException::new);

        return financialTransactions.stream()
                .map(financialTransaction -> new FinancialTransactionResponseDTO(
                        financialTransaction.getId(),
                        financialTransaction.getTitle(),
                        financialTransaction.getType(),
                        financialTransaction.getTransactionDate(),
                        financialTransaction.getAmount()
                ))
                .collect(Collectors.toList());
    }

}
