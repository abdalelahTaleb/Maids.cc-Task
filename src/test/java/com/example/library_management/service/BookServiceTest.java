package com.example.library_management.service;

import com.example.library_management.dto.BookDTO;
import com.example.library_management.entity.Book;
import com.example.library_management.exception.NotFoundException;
import com.example.library_management.mapper.BookMapper;
import com.example.library_management.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private BookDTO bookDTO;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Spring Boot Guide");
        book.setIsbn("123-456");

        bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setTitle("Spring Boot Guide");
        bookDTO.setIsbn("123-456");
    }

    @Test
    void getBookById_ShouldReturnBook_WhenBookExists() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toDTO(book)).thenReturn(bookDTO);

        BookDTO foundBook = bookService.getBookById(1L).orElse(null);

        assertNotNull(foundBook);
        assertEquals("Spring Boot Guide", foundBook.getTitle());
    }

    @Test
    void getBookById_ShouldThrowException_WhenBookNotFound() {
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.getBookById(2L));
    }
}
