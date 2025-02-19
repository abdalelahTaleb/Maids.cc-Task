package com.example.library_management.repository;

import com.example.library_management.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepository extends JpaRepository<Patron, Long> {

}