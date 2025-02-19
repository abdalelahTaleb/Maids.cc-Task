package com.example.library_management.repository;

import com.example.library_management.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    // يمكن إضافة استعلامات مخصصة هنا إذا لزم الأمر
}