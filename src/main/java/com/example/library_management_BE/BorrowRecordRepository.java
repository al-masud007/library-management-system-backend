package com.example.library_management_BE;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    Optional<BorrowRecord> findByUserIdAndBookId(Long userId, Long bookId);
}
