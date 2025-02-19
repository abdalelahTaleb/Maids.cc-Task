package com.example.library_management.service;

import com.example.library_management.dto.BookDTO;
import com.example.library_management.entity.Book;
import com.example.library_management.mapper.BookMapper;
import com.example.library_management.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper ;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    // جلب جميع الكتب كـ DTOs
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDTO)
                .toList();
    }

    // جلب كتاب معين كـ DTO
    public Optional<BookDTO> getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDTO);
    }

    // إضافة كتاب جديد باستخدام DTO
    public BookDTO addBook(BookDTO bookDTO) {
        Book book = bookMapper.toEntity(bookDTO);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDTO(savedBook);
    }

    // تحديث كتاب باستخدام DTO
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    Book updatedBook = bookMapper.toEntity(bookDTO);
                    updatedBook.setId(existingBook.getId());
                    return bookMapper.toDTO(bookRepository.save(updatedBook));
                })
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
    }

    // حذف كتاب
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
