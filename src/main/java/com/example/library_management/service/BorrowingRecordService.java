package com.example.library_management.service;

import com.example.library_management.dto.BorrowingRecordDTO;
import com.example.library_management.entity.Book;
import com.example.library_management.entity.BorrowingRecord;
import com.example.library_management.entity.Patron;
import com.example.library_management.exception.*;
import com.example.library_management.mapper.BorrowingRecordMapper;
import com.example.library_management.repository.BookRepository;
import com.example.library_management.repository.BorrowingRecordRepository;
import com.example.library_management.repository.PatronRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowingRecordService {

    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;
    private final BorrowingRecordMapper borrowingRecordMapper;


    public BorrowingRecordService(BorrowingRecordRepository borrowingRecordRepository,
                                  BookRepository bookRepository,
                                  PatronRepository patronRepository,
                                  BorrowingRecordMapper borrowingRecordMapper) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
        this.borrowingRecordMapper = borrowingRecordMapper;
    }

    // 🟢 جلب جميع سجلات الاستعارة
    public List<BorrowingRecordDTO> getAllBorrowingRecords() {
        return borrowingRecordRepository.findAll().stream()
                .map(borrowingRecordMapper::toDTO)
                .toList();
    }

    // 🔴 استعارة كتاب
    @Transactional
    public BorrowingRecordDTO borrowBook(BorrowingRecordDTO recordDTO) {
        // 🟠 التحقق مما إذا كان الكتاب موجودًا
        Book book = bookRepository.findById(recordDTO.getBookId())
                .orElseThrow(() -> new NotFoundException("Book not found with id " + recordDTO.getBookId()));

        // 🟠 التحقق مما إذا كان المستفيد موجودًا
        Patron patron = patronRepository.findById(recordDTO.getPatronId())
                .orElseThrow(() -> new NotFoundException("Patron not found with id " + recordDTO.getPatronId()));

        // 🟠 التحقق مما إذا كان الكتاب متاحًا
        if (book.getCopiesAvailable() <= 0) {
            throw new BookNotAvailableException("Book is currently unavailable for borrowing.");
        }

        // ✅ إنشاء سجل استعارة جديد
        BorrowingRecord record = borrowingRecordMapper.toEntity(recordDTO);
        record.setBook(book);
        record.setPatron(patron);
        record.setBorrowingDate(LocalDate.now());

        // 🔴 تقليل عدد النسخ المتاحة من الكتاب
        book.setCopiesAvailable(book.getCopiesAvailable() - 1);
        bookRepository.save(book);

        BorrowingRecord savedRecord = borrowingRecordRepository.save(record);
        return borrowingRecordMapper.toDTO(savedRecord);
    }

    // 🔴 إرجاع كتاب
    @Transactional
    public BorrowingRecordDTO returnBook(Long recordId) {
        // 🟠 البحث عن السجل
        BorrowingRecord record = borrowingRecordRepository.findById(recordId)
                .orElseThrow(() -> new NotFoundException("Borrowing Record not found with id " + recordId));

        // ✅ تحديد تاريخ الإرجاع
        record.setReturnDate(LocalDate.now());

        // ✅ زيادة عدد النسخ المتاحة من الكتاب
        Book book = record.getBook();
        book.setCopiesAvailable(book.getCopiesAvailable() + 1);
        bookRepository.save(book);

        return borrowingRecordMapper.toDTO(borrowingRecordRepository.save(record));
    }
}
