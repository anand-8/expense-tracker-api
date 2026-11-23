
package com.anand.expensetrackerapi.repository;

import com.anand.expensetrackerapi.entity.Transaction;
import com.anand.expensetrackerapi.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import java.util.List;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserId(Long userId);

    List<Transaction> findByUserIdAndType(
            Long userId,
            TransactionType type
    );
    Optional<Transaction> findByIdAndUserId(Long id, Long userId);
}
