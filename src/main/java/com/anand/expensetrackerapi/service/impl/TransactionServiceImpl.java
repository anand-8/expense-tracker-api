
package com.anand.expensetrackerapi.service.impl;

import com.anand.expensetrackerapi.entity.Transaction;
import com.anand.expensetrackerapi.entity.User;
import com.anand.expensetrackerapi.enums.TransactionType;
import com.anand.expensetrackerapi.repository.TransactionRepository;
import com.anand.expensetrackerapi.repository.UserRepository;
import com.anand.expensetrackerapi.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.anand.expensetrackerapi.dto.FinancialSummaryResponse;
import java.math.BigDecimal;
import com.anand.expensetrackerapi.enums.TransactionType;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Transaction createTransaction(
            Transaction transaction,
            Long userId
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found"));

        validateTransaction(transaction);

        transaction.setUser(user);

        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> getUserTransactions(Long userId) {

        return transactionRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Transaction getTransactionById(
            Long transactionId,
            Long userId
    ) {

        return transactionRepository
                .findByIdAndUserId(transactionId, userId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Transaction not found"));
    }

    @Override
    @Transactional
    public Transaction updateTransaction(
            Long transactionId,
            Transaction updatedTransaction,
            Long userId
    ) {

        Transaction existingTransaction =
                getTransactionById(transactionId, userId);

        validateTransaction(updatedTransaction);

        existingTransaction.setAmount(updatedTransaction.getAmount());
        existingTransaction.setDescription(updatedTransaction.getDescription());
        existingTransaction.setCategory(updatedTransaction.getCategory());
        existingTransaction.setType(updatedTransaction.getType());
        existingTransaction.setTransactionDate(
                updatedTransaction.getTransactionDate());

        return transactionRepository.save(existingTransaction);
    }

    @Override
    @Transactional
    public void deleteTransaction(Long transactionId, Long userId) {

        Transaction transaction =
                getTransactionById(transactionId, userId);

        transactionRepository.delete(transaction);
    }

    private void validateTransaction(Transaction transaction) {

        if (transaction.getAmount() == null ||
                transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Transaction amount must be greater than zero");
        }

        if (transaction.getDescription() == null ||
                transaction.getDescription().isBlank()) {

            throw new IllegalArgumentException(
                    "Description cannot be empty");
        }

        if (transaction.getCategory() == null ||
                transaction.getCategory().isBlank()) {

            throw new IllegalArgumentException(
                    "Category cannot be empty");
        }

        if (transaction.getType() == null) {
            throw new IllegalArgumentException(
                    "Transaction type is required");
        }

        if (transaction.getTransactionDate() == null) {
            throw new IllegalArgumentException(
                    "Transaction date is required");
        }
    }


    @Override
    public FinancialSummaryResponse getFinancialSummary(Long userId) {

        BigDecimal totalIncome =
                transactionRepository.calculateTotalByUserAndType(
                        userId, TransactionType.INCOME
                );

        BigDecimal totalExpenses =
                transactionRepository.calculateTotalByUserAndType(
                        userId, TransactionType.EXPENSE
                );

        BigDecimal balance = totalIncome.subtract(totalExpenses);

        long totalTransactions =
                transactionRepository.countByUserId(userId);

        return new FinancialSummaryResponse(
                totalIncome,
                totalExpenses,
                balance,
                totalTransactions
        );
    }

}
