package com.example.library_management.service;

import com.example.library_management.dto.PatronDTO;
import com.example.library_management.entity.Patron;
import com.example.library_management.exception.NotFoundException;
import com.example.library_management.mapper.PatronMapper;
import com.example.library_management.repository.PatronRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {

    private final PatronRepository patronRepository;
    private final PatronMapper patronMapper;

    public PatronService(PatronRepository patronRepository, PatronMapper patronMapper) {
        this.patronRepository = patronRepository;
        this.patronMapper = patronMapper;
    }

    public List<PatronDTO> getAllPatrons() {
        return patronRepository.findAll().stream()
                .map(patronMapper::toDTO)
                .toList();
    }

    public Optional<PatronDTO> getPatronById(Long id) {
        return patronRepository.findById(id)
                .map(patronMapper::toDTO)
                .or(() -> { throw new NotFoundException("Patron not found with id " + id); });
    }

    public PatronDTO addPatron(PatronDTO patronDTO) {
        Patron patron = patronMapper.toEntity(patronDTO);
        Patron savedPatron = patronRepository.save(patron);
        return patronMapper.toDTO(savedPatron);
    }

    public PatronDTO updatePatron(Long id, PatronDTO patronDTO) {
        Patron patron = patronRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patron not found with id " + id));

        Patron updatedPatron = patronMapper.toEntity(patronDTO);
        updatedPatron.setId(patron.getId());

        return patronMapper.toDTO(patronRepository.save(updatedPatron));
    }

    public void deletePatron(Long id) {
        if (!patronRepository.existsById(id)) {
            throw new NotFoundException("Patron not found with id " + id);
        }
        patronRepository.deleteById(id);
    }
}
