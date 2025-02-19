package com.example.library_management.mapper;

import com.example.library_management.dto.BorrowingRecordDTO;
import com.example.library_management.entity.BorrowingRecord;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BorrowingRecordMapper {

    BorrowingRecordDTO toDTO(BorrowingRecord borrowingRecord);
    BorrowingRecord toEntity(BorrowingRecordDTO borrowingRecordDTO);
}
