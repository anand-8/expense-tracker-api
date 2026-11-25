package com.anand.expensetrackerapi.dto;

import java.math.BigDecimal;

public record FinancialSummaryResponse(
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        BigDecimal balance,
        long totalTransactions
){

}
