package com.example.library_management.service;

import com.example.library_management.dto.PatronDTO;
import com.example.library_management.entity.Patron;
import com.example.library_management.mapper.PatronMapper;
import com.example.library_management.repository.PatronRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {

    private final PatronRepository patronRepository;
    private final PatronMapper patronMapper ;

    public PatronService(PatronRepository patronRepository, PatronMapper patronMapper) {
        this.patronRepository = patronRepository;
        this.patronMapper = patronMapper;
    }

    public List<PatronDTO> getAllPatrons() {
        List<PatronDTO> patrons = patronRepository.findAll().stream()
                .map(patronMapper::toDTO)
                .toList();

        // ✅ تحقق من القيم المعادة
        patrons.forEach(p -> System.out.println("🚀 PatronDTO: " + p));

        return patrons;
    }
    // جلب مستفيد معين كـ DTO
    public Optional<PatronDTO> getPatronById(Long id) {
        return patronRepository.findById(id)
                .map(patronMapper::toDTO);
    }

    // إضافة مستفيد جديد باستخدام DTO
    public PatronDTO addPatron(PatronDTO patronDTO) {
        Patron patron = patronMapper.toEntity(patronDTO);
        Patron savedPatron = patronRepository.save(patron);
        return patronMapper.toDTO(savedPatron);
    }

    // تحديث مستفيد باستخدام DTO
    public PatronDTO updatePatron(Long id, PatronDTO patronDTO) {
        return patronRepository.findById(id)
                .map(existingPatron -> {
                    Patron updatedPatron = patronMapper.toEntity(patronDTO);
                    updatedPatron.setId(existingPatron.getId());
                    return patronMapper.toDTO(patronRepository.save(updatedPatron));
                })
                .orElseThrow(() -> new RuntimeException("Patron not found with id " + id));
    }

    // حذف مستفيد
    public void deletePatron(Long id) {
        patronRepository.deleteById(id);
    }
}
