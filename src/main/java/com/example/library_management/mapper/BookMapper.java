package com.example.library_management.mapper;

import com.example.library_management.dto.BookDTO;
import com.example.library_management.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO toDTO(Book book);
    Book toEntity(BookDTO bookDTO);
}
