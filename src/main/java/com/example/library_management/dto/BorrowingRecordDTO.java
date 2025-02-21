package com.example.library_management.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.AssertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BorrowingRecordDTO {
    private Long id;

    @NotNull(message = "Book ID cannot be null")
    private Long bookId;

    @NotNull(message = "Patron ID cannot be null")
    private Long patronId;

    @NotNull(message = "Borrowing date cannot be null")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Borrowing date must be in format yyyy-MM-dd")
    private String borrowingDate;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Return date must be in format yyyy-MM-dd")
    private String returnDate;

    private boolean isAvailable; // 🟢 جديد: لمعرفة ما إذا كان الكتاب متاحًا للاستعارة أم لا

    @AssertTrue(message = "Return date must be after borrowing date")
    public boolean isReturnDateValid() {
        if (returnDate == null || borrowingDate == null) {
            return true; // لا مشكلة إن لم يتم إدخال تاريخ الإرجاع
        }
        try {
            LocalDate borrowDate = LocalDate.parse(borrowingDate);
            LocalDate returnDateValue = LocalDate.parse(returnDate);
            return returnDateValue.isAfter(borrowDate);
        } catch (DateTimeParseException e) {
            return false; // إذا كان هناك مشكلة في التحليل، يعتبر غير صالح
        }
    }
}
