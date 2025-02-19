package com.example.library_management.service;

import com.example.library_management.dto.BorrowingRecordDTO;
import com.example.library_management.entity.BorrowingRecord;
import com.example.library_management.mapper.BorrowingRecordMapper;
import com.example.library_management.repository.BorrowingRecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowingRecordService {

    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BorrowingRecordMapper borrowingRecordMapper ;

    public BorrowingRecordService(BorrowingRecordRepository borrowingRecordRepository, BorrowingRecordMapper borrowingRecordMapper) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.borrowingRecordMapper = borrowingRecordMapper;
    }

    // جلب جميع سجلات الاستعارة كـ DTOs
    public List<BorrowingRecordDTO> getAllBorrowingRecords() {
        return borrowingRecordRepository.findAll().stream()
                .map(borrowingRecordMapper::toDTO)
                .toList();
    }

    // إضافة سجل استعارة جديد باستخدام DTO
    public BorrowingRecordDTO borrowBook(BorrowingRecordDTO recordDTO) {
        BorrowingRecord record = borrowingRecordMapper.toEntity(recordDTO);
        record.setBorrowingDate(LocalDate.now());
        BorrowingRecord savedRecord = borrowingRecordRepository.save(record);
        return borrowingRecordMapper.toDTO(savedRecord);
    }

    // إرجاع كتاب
    public BorrowingRecordDTO returnBook(Long recordId) {
        return borrowingRecordRepository.findById(recordId)
                .map(record -> {
                    record.setReturnDate(LocalDate.now());
                    return borrowingRecordMapper.toDTO(borrowingRecordRepository.save(record));
                })
                .orElseThrow(() -> new RuntimeException("Borrowing Record not found with id " + recordId));
    }
}
