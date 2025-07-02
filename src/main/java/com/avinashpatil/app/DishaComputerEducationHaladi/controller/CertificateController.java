package com.avinashpatil.app.DishaComputerEducationHaladi.controller;


import com.avinashpatil.app.DishaComputerEducationHaladi.dtos.CertificateDTO;
import com.avinashpatil.app.DishaComputerEducationHaladi.service.CertificateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @PostMapping
    public ResponseEntity<CertificateDTO> generateCertificate(@Valid @RequestBody CertificateDTO certificateDTO) {
        CertificateDTO generatedCertificate = certificateService.generateCertificate(certificateDTO);
        return ResponseEntity.ok(generatedCertificate);
    }

    @PostMapping("/batch/{batchId}")
    public ResponseEntity<Void> generateCertificatesForBatch(@PathVariable String batchId) {
        certificateService.generateCertificatesForBatch(batchId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{certificateId}/send")
    public ResponseEntity<Void> sendCertificate(@PathVariable String certificateId) {
        certificateService.sendCertificateToUser(certificateId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{certificateId}")
    public ResponseEntity<CertificateDTO> getCertificate(@PathVariable String certificateId) {
        CertificateDTO certificate = certificateService.getCertificate(certificateId);
        return ResponseEntity.ok(certificate);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CertificateDTO>> getCertificatesByUser(@PathVariable String userId) {
        List<CertificateDTO> certificates = certificateService.getCertificatesByUser(userId);
        return ResponseEntity.ok(certificates);
    }
}
