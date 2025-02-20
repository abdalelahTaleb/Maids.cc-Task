package com.example.library_management.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookDTO {
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Author cannot be empty")
    private String author;

    @Min(value = 1500, message = "Publication year must be greater than 1500")
    @Max(value = 2100, message = "Publication year cannot be in the future")
    private int publicationYear;

    @NotBlank(message = "ISBN cannot be empty")
    private String isbn;

    @Min(value = 0, message = "Copies available cannot be negative")
    private int copiesAvailable;
}
