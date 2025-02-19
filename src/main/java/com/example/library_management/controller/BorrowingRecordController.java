package com.example.library_management.controller;

import com.example.library_management.dto.BorrowingRecordDTO;
import com.example.library_management.service.BorrowingRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowing-records")
public class BorrowingRecordController {

    private final BorrowingRecordService borrowingRecordService;

    public BorrowingRecordController(BorrowingRecordService borrowingRecordService) {
        this.borrowingRecordService = borrowingRecordService;
    }

    // جلب جميع سجلات الاستعارة
    @GetMapping
    public ResponseEntity<List<BorrowingRecordDTO>> getAllBorrowingRecords() {
        List<BorrowingRecordDTO> records = borrowingRecordService.getAllBorrowingRecords();
        return ResponseEntity.ok(records);
    }

    // إضافة سجل استعارة جديد
    @PostMapping
    public ResponseEntity<BorrowingRecordDTO> borrowBook(@RequestBody BorrowingRecordDTO recordDTO) {
        BorrowingRecordDTO savedRecord = borrowingRecordService.borrowBook(recordDTO);
        return ResponseEntity.ok(savedRecord);
    }

    // إرجاع كتاب
    @PutMapping("/return/{id}")
    public ResponseEntity<BorrowingRecordDTO> returnBook(@PathVariable Long id) {
        BorrowingRecordDTO returnedRecord = borrowingRecordService.returnBook(id);
        return ResponseEntity.ok(returnedRecord);
    }
}
