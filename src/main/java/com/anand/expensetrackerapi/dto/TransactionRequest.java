
package com.anand.expensetrackerapi.dto;

import com.anand.expensetrackerapi.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequest(

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
        BigDecimal amount,

        @NotBlank(message = "Description is required")
        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description,

        @NotBlank(message = "Category is required")
        @Size(max = 50, message = "Category cannot exceed 50 characters")
        String category,

        @NotNull(message = "Transaction type is required")
        TransactionType type,

        @NotNull(message = "Transaction date is required")
        LocalDate transactionDate

) {}
