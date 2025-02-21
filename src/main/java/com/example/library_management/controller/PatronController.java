package com.example.library_management.controller;

import com.example.library_management.dto.PatronDTO;
import com.example.library_management.service.PatronService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    private final PatronService patronService;

    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    // جلب جميع المستفيدين
    @GetMapping
    public ResponseEntity<List<PatronDTO>> getAllPatrons() {
        return ResponseEntity.ok(patronService.getAllPatrons());
    }

    // جلب مستفيد معين حسب ID
    @GetMapping("/{id}")
    public ResponseEntity<PatronDTO> getPatronById(@PathVariable Long id) {
        Optional<PatronDTO> patronDTO = patronService.getPatronById(id);
        return ResponseEntity.notFound().build();
    }


    // إضافة مستفيد جديد
    @PostMapping
    public ResponseEntity<PatronDTO> addPatron(@Valid @RequestBody PatronDTO patronDTO) {
        return ResponseEntity.ok(patronService.addPatron(patronDTO));
    }

    // تحديث مستفيد
    @PutMapping("/{id}")
    public ResponseEntity<PatronDTO> updatePatron( @PathVariable Long id, @Valid @RequestBody PatronDTO patronDTO) {
        return ResponseEntity.ok(patronService.updatePatron(id, patronDTO));
    }

    // حذف مستفيد
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable Long id) {
        patronService.deletePatron(id);
        return ResponseEntity.noContent().build();
    }
}
