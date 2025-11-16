package com.healthcare.patient.controller;

import com.healthcare.patient.dto.PatientRequestDTO;
import com.healthcare.patient.dto.PatientResponseDTO;
import com.healthcare.patient.dto.response.UserResponse;
import com.healthcare.patient.service.PatientService;
import feign.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients")

public class PatientController {

    @Autowired
    private PatientService patientService;


    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(
            @Valid @RequestBody PatientRequestDTO requestDTO,
            @RequestHeader(value = "X-User-Id", defaultValue = "system") String userId) {
        PatientResponseDTO response = patientService.createPatient(requestDTO, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequestDTO requestDTO,
            @RequestHeader(value = "X-User-Id", defaultValue = "system") String userId) {
        PatientResponseDTO response = patientService.updatePatient(id, requestDTO, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")

    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable Long id) {
        PatientResponseDTO response = patientService.getPatientById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/code/{code}")

    public ResponseEntity<PatientResponseDTO> getPatientByCode(@PathVariable String code) {
        PatientResponseDTO response = patientService.getPatientByCode(code);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")

    public ResponseEntity<List<PatientResponseDTO>> getActivePatients() {
        List<PatientResponseDTO> response = patientService.getActivePatients();
        return ResponseEntity.ok(response);
    }

    @GetMapping

    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        List<PatientResponseDTO> response = patientService.getAllPatients();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")

    public ResponseEntity<List<PatientResponseDTO>> searchPatients(@RequestParam String keyword) {
        List<PatientResponseDTO> response = patientService.searchPatients(keyword);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<Void> deletePatient(
            @PathVariable Long id,
           String userId) {
        patientService.deletePatient(id, userId);
        return ResponseEntity.noContent().build();
    }
}

