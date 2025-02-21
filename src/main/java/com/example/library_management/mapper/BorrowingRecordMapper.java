package com.example.library_management.mapper;

import com.example.library_management.dto.BorrowingRecordDTO;
import com.example.library_management.entity.BorrowingRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BorrowingRecordMapper {

    @Mapping(source = "book.id", target = "bookId")  // ✅ تأكد من جلب bookId
    @Mapping(source = "patron.id", target = "patronId")  // ✅ تأكد من جلب patronId
    BorrowingRecordDTO toDTO(BorrowingRecord borrowingRecord);

    @Mapping(source = "bookId", target = "book.id")  // ✅ تأكد من تحويل bookId إلى كيان Book
    @Mapping(source = "patronId", target = "patron.id")  // ✅ تأكد من تحويل patronId إلى كيان Patron
    BorrowingRecord toEntity(BorrowingRecordDTO borrowingRecordDTO);
}
