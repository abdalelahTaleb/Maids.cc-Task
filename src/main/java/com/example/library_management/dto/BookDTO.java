package com.example.library_management.dto;

import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private int publicationYear;
    private String isbn;
    private int copiesAvailable;

    // Getters and Setters
}
