package com.example.library_management.service;

import com.example.library_management.dto.BorrowingRecordDTO;
import com.example.library_management.entity.Book;
import com.example.library_management.entity.BorrowingRecord;
import com.example.library_management.entity.Patron;
import com.example.library_management.exception.BookNotAvailableException;
import com.example.library_management.mapper.BorrowingRecordMapper;
import com.example.library_management.repository.BookRepository;
import com.example.library_management.repository.BorrowingRecordRepository;
import com.example.library_management.repository.PatronRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BorrowingRecordServiceTest {

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PatronRepository patronRepository;

    @Mock
    private BorrowingRecordMapper borrowingRecordMapper;

    @InjectMocks
    private BorrowingRecordService borrowingRecordService;

    private Book book;
    private Patron patron;
    private BorrowingRecord borrowingRecord;
    private BorrowingRecordDTO borrowingRecordDTO;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setAvailable(true);

        patron = new Patron();
        patron.setId(1L);

        borrowingRecord = new BorrowingRecord();
        borrowingRecord.setId(1L);
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);

        borrowingRecordDTO = new BorrowingRecordDTO();
        borrowingRecordDTO.setId(1L);
        borrowingRecordDTO.setBookId(1L);
        borrowingRecordDTO.setPatronId(1L);
    }

    @Test
    void borrowBook_ShouldThrowException_WhenBookIsUnavailable() {
        book.setAvailable(false);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        assertThrows(BookNotAvailableException.class, () -> borrowingRecordService.borrowBook(borrowingRecordDTO));
    }

    @Test
    void borrowBook_ShouldCreateRecord_WhenBookIsAvailable() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));
        when(borrowingRecordRepository.save(any())).thenReturn(borrowingRecord);
        when(borrowingRecordMapper.toDTO(any())).thenReturn(borrowingRecordDTO);

        BorrowingRecordDTO result = borrowingRecordService.borrowBook(borrowingRecordDTO);

        assertNotNull(result);
        verify(borrowingRecordRepository, times(1)).save(any());
    }
}
