
package com.anand.expensetrackerapi.controller;

import com.anand.expensetrackerapi.dto.TransactionRequest;
import com.anand.expensetrackerapi.dto.TransactionResponse;
import com.anand.expensetrackerapi.entity.Transaction;
import com.anand.expensetrackerapi.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.anand.expensetrackerapi.dto.FinancialSummaryResponse;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    // Create transaction
    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @Valid @RequestBody TransactionRequest request,
            @RequestParam Long userId) {

        Transaction transaction = new Transaction();

        transaction.setAmount(request.amount());
        transaction.setDescription(request.description());
        transaction.setCategory(request.category());
        transaction.setType(request.type());
        transaction.setTransactionDate(request.transactionDate());

        Transaction savedTransaction =
                transactionService.createTransaction(transaction, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapToResponse(savedTransaction));
    }

    // Get all transactions for a user
    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getUserTransactions(
            @RequestParam Long userId) {

        List<TransactionResponse> response =
                transactionService.getUserTransactions(userId)
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }

    // Get transaction by ID
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(
            @PathVariable Long id,
            @RequestParam Long userId) {

        Transaction transaction =
                transactionService.getTransactionById(id, userId);

        return ResponseEntity.ok(mapToResponse(transaction));
    }

    // Update transaction
    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody TransactionRequest request,
            @RequestParam Long userId) {

        Transaction updatedTransaction = new Transaction();

        updatedTransaction.setAmount(request.amount());
        updatedTransaction.setDescription(request.description());
        updatedTransaction.setCategory(request.category());
        updatedTransaction.setType(request.type());
        updatedTransaction.setTransactionDate(request.transactionDate());

        Transaction result = transactionService.updateTransaction(
                id, updatedTransaction, userId);

        return ResponseEntity.ok(mapToResponse(result));
    }

    // Delete transaction
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(
            @PathVariable Long id,
            @RequestParam Long userId) {

        transactionService.deleteTransaction(id, userId);

        return ResponseEntity.noContent().build();
    }

    //Get Financial Summary
    @GetMapping("/summary")
    public ResponseEntity<FinancialSummaryResponse> getFinancialSummary(
            @RequestParam Long userId
    ){
        FinancialSummaryResponse summary =
                transactionService.getFinancialSummary(userId);
        return ResponseEntity.ok(summary);
    }

    // Convert Entity into Response DTO
    private TransactionResponse mapToResponse(Transaction transaction) {

        return new TransactionResponse(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getDescription(),
                transaction.getCategory(),
                transaction.getType(),
                transaction.getTransactionDate(),
                transaction.getUser().getId(),
                transaction.getCreatedAt()
        );
    }
}
