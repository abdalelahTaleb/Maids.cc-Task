package com.example.library_management.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // اجعل Jackson لا يحذف الحقول الفارغة
public class PatronDTO {
    private Long id;
    private String name;
    private String email;
}
