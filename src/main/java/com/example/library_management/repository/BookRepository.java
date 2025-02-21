package com.example.library_management.repository;

import com.example.library_management.entity.Book;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(@NotBlank(message = "ISBN cannot be empty") String isbn);
    // يمكن إضافة استعلامات مخصصة هنا إذا لزم الأمر
}