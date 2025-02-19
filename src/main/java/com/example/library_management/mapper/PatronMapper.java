package com.example.library_management.mapper;

import com.example.library_management.dto.PatronDTO;
import com.example.library_management.entity.Patron;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatronMapper {

    PatronDTO toDTO(Patron patron);
    Patron toEntity(PatronDTO patronDTO);
}
