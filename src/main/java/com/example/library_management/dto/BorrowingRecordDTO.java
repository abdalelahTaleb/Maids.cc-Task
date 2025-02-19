package com.example.library_management.dto;

import lombok.Data;

@Data
public class BorrowingRecordDTO {
    private Long id;
    private Long bookId;
    private Long patronId;
    private String borrowingDate;
    private String returnDate;

    // Getters and Setters
}
