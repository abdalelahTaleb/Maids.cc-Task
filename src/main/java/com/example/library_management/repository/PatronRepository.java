package com.example.library_management.repository;

import com.example.library_management.entity.Patron;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface PatronRepository extends JpaRepository<Patron, Long> {

    Optional<Patron> findByEmail(String email);  // ✅ تأكد أن النوع هو Optional وليس Stream
    Optional<Patron> findByPhoneNumber(String phoneNumber);

}