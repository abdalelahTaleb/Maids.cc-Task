package com.example.library_management.service;

import com.example.library_management.dto.PatronDTO;
import com.example.library_management.entity.Patron;
import com.example.library_management.exception.NotFoundException;
import com.example.library_management.mapper.PatronMapper;
import com.example.library_management.repository.PatronRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatronServiceTest {

    @Mock
    private PatronRepository patronRepository;

    @Mock
    private PatronMapper patronMapper;

    @InjectMocks
    private PatronService patronService;

    private Patron patron;
    private PatronDTO patronDTO;

    @BeforeEach
    void setUp() {
        patron = new Patron();
        patron.setId(1L);
        patron.setName("John Doe");

        patronDTO = new PatronDTO();
        patronDTO.setId(1L);
        patronDTO.setName("John Doe");
    }

    @Test
    void getPatronById_ShouldReturnPatron_WhenPatronExists() {
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));
        when(patronMapper.toDTO(patron)).thenReturn(patronDTO);

        PatronDTO foundPatron = patronService.getPatronById(1L).orElse(null);

        assertNotNull(foundPatron);
        assertEquals("John Doe", foundPatron.getName());
    }

    @Test
    void getPatronById_ShouldThrowException_WhenPatronNotFound() {
        when(patronRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> patronService.getPatronById(2L));
    }
}
