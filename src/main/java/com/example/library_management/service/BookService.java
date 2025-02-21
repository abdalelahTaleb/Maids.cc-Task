package com.example.library_management.service;

import com.example.library_management.dto.BookDTO;
import com.example.library_management.entity.Book;
import com.example.library_management.exception.ConflictException;
import com.example.library_management.exception.NotFoundException;
import com.example.library_management.mapper.BookMapper;
import com.example.library_management.repository.BookRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }
    @Cacheable("books")
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDTO)
                .toList();
    }
    @Cacheable(value = "book", key = "#id")
    public Optional<BookDTO> getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDTO)
                .or(() -> { throw new NotFoundException("Book not found with id " + id); });
    }
    @CacheEvict(value = "books", allEntries = true)

    public BookDTO addBook(BookDTO bookDTO) {
        // التحقق مما إذا كان هناك كتاب بنفس الـ ISBN قبل الإدراج
        if (bookRepository.findByIsbn(bookDTO.getIsbn()).isPresent()) {
            throw new ConflictException("A book with the same ISBN already exists.");
        }

        // تحويل الـ DTO إلى كيان وحفظه
        Book book = bookMapper.toEntity(bookDTO);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDTO(savedBook);
    }
    @CacheEvict(value = {"books", "book"}, key = "#id")

    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        // البحث عن الكتاب الحالي
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found with id " + id));

        // التحقق من عدم تكرار ISBN في كتاب آخر غير الكتاب الحالي
        bookRepository.findByIsbn(bookDTO.getIsbn())
                .filter(existingBook -> !existingBook.getId().equals(id)) // تجاهل نفس الكتاب
                .ifPresent(existingBook -> {
                    throw new ConflictException("A book with the same ISBN already exists.");
                });

        // الاحتفاظ بالقيم القديمة التي لا يجب تعديلها
        Book updatedBook = bookMapper.toEntity(bookDTO);
        updatedBook.setId(book.getId()); // الحفاظ على نفس الـ ID

        // حفظ التعديلات
        return bookMapper.toDTO(bookRepository.save(updatedBook));
    }


    @CacheEvict(value = {"books", "book"}, key = "#id")

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new NotFoundException("Book not found with id " + id);
        }
        bookRepository.deleteById(id);
    }
}
