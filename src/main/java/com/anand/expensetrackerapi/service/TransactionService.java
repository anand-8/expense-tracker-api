
package com.anand.expensetrackerapi.service;

import com.anand.expensetrackerapi.entity.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(
            Transaction transaction,
            Long userId
    );

    List<Transaction> getUserTransactions(Long userId);

    Transaction getTransactionById(Long transactionId, Long userId);

    Transaction updateTransaction(
            Long transactionId,
            Transaction updatedTransaction,
            Long userId
    );

    void deleteTransaction(Long transactionId, Long userId);
}
