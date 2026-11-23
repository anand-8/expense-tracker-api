
package com.anand.expensetrackerapi.dto;

import com.anand.expensetrackerapi.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransactionResponse(

        Long id,
        BigDecimal amount,
        String description,
        String category,
        TransactionType type,
        LocalDate transactionDate,
        Long userId,
        LocalDateTime createdAt

) {}
