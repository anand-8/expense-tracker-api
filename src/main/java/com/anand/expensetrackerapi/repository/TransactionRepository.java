
package com.anand.expensetrackerapi.repository;

import com.anand.expensetrackerapi.entity.Transaction;
import com.anand.expensetrackerapi.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
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

    @Query("""
            SELECT COALESCE(SUM(t.amount), 0)
                        FROM Transaction t
                        WHERE t.user.id = :userId
                        AND t.type = :type
            """)
    BigDecimal calculateTotalByUserAndType(
            @Param("userId") Long userId,
            @Param("type") TransactionType type
    );
    @Query("""
            SELECT COUNT(t)
            FROM Transaction t
            WHERE t.user.id = :userId
""")
    long countByUserId(@Param("userId") Long userId);
}
