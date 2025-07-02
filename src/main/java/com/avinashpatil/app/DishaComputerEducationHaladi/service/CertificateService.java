package com.avinashpatil.app.DishaComputerEducationHaladi.service;

import com.avinashpatil.app.DishaComputerEducationHaladi.dtos.CertificateDTO;

import java.util.List;

public interface CertificateService {
    CertificateDTO generateCertificate(CertificateDTO certificateDTO);
    void generateCertificatesForBatch(String batchId);
    void sendCertificateToUser(String certificateId);
    CertificateDTO getCertificate(String certificateId);
    List<CertificateDTO> getCertificatesByUser(String userId);
}