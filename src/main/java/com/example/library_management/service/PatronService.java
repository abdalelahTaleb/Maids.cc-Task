package com.example.library_management.service;

import com.example.library_management.dto.PatronDTO;
import com.example.library_management.entity.Patron;
import com.example.library_management.exception.ConflictException;
import com.example.library_management.exception.NotFoundException;
import com.example.library_management.mapper.PatronMapper;
import com.example.library_management.repository.PatronRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable("patrons")

    public List<PatronDTO> getAllPatrons() {
        return patronRepository.findAll().stream()
                .map(patronMapper::toDTO)
                .toList();
    }
    @Cacheable(value = "patron", key = "#id")

    public Optional<PatronDTO> getPatronById(Long id) {
        return patronRepository.findById(id)
                .map(patronMapper::toDTO);
    }
    @CacheEvict(value = "patrons", allEntries = true)

    public PatronDTO addPatron(PatronDTO patronDTO) {
        // ✅ تحقق من وجود البريد الإلكتروني أو رقم الهاتف مسبقًا
        if (patronRepository.findByEmail(patronDTO.getEmail()).isPresent()) {
            throw new ConflictException("Email is already in use.");
        }
        if (patronRepository.findByPhoneNumber(patronDTO.getPhoneNumber()).isPresent()) {
            throw new ConflictException("Phone number is already in use.");
        }

        Patron patron = patronMapper.toEntity(patronDTO);
        Patron savedPatron = patronRepository.save(patron);
        return patronMapper.toDTO(savedPatron);
    }
    @CacheEvict(value = {"patrons", "patron"}, key = "#id")

    public PatronDTO updatePatron(Long id, PatronDTO patronDTO) {
        Patron patron = patronRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patron not found with id " + id));

        // ✅ تحقق إذا كان البريد الإلكتروني أو الهاتف موجود مسبقًا لمستخدم آخر
        patronRepository.findByEmail(patronDTO.getEmail())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> { throw new ConflictException("Email is already in use."); });

        patronRepository.findByPhoneNumber(patronDTO.getPhoneNumber())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> { throw new ConflictException("Phone number is already in use."); });

        patron.setName(patronDTO.getName());
        patron.setEmail(patronDTO.getEmail());
        patron.setPhoneNumber(patronDTO.getPhoneNumber());

        return patronMapper.toDTO(patronRepository.save(patron));
    }
    @CacheEvict(value = {"patrons", "patron"}, key = "#id")

    public void deletePatron(Long id) {
        if (!patronRepository.existsById(id)) {
            throw new NotFoundException("Patron not found with id " + id);
        }
        patronRepository.deleteById(id);
    }
}
