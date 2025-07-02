package com.avinashpatil.app.DishaComputerEducationHaladi.service.Impl;

import com.avinashpatil.app.DishaComputerEducationHaladi.dtos.CertificateDTO;
import com.avinashpatil.app.DishaComputerEducationHaladi.entity.Batch;
import com.avinashpatil.app.DishaComputerEducationHaladi.entity.Certificate;
import com.avinashpatil.app.DishaComputerEducationHaladi.entity.Course;
import com.avinashpatil.app.DishaComputerEducationHaladi.entity.User;
import com.avinashpatil.app.DishaComputerEducationHaladi.exceptions.BadRequestException;
import com.avinashpatil.app.DishaComputerEducationHaladi.exceptions.ResourceNotFoundException;
import com.avinashpatil.app.DishaComputerEducationHaladi.repositories.BatchRepository;
import com.avinashpatil.app.DishaComputerEducationHaladi.repositories.CertificateRepository;
import com.avinashpatil.app.DishaComputerEducationHaladi.repositories.CourseRepository;
import com.avinashpatil.app.DishaComputerEducationHaladi.repositories.UserRepository;
import com.avinashpatil.app.DishaComputerEducationHaladi.service.CertificateService;
import com.avinashpatil.app.DishaComputerEducationHaladi.service.EmailService;
import com.avinashpatil.app.DishaComputerEducationHaladi.service.PDFGenerationService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl implements CertificateService {

    private static final Logger logger = LoggerFactory.getLogger(CertificateServiceImpl.class);

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PDFGenerationService pdfGenerationService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CertificateDTO generateCertificate(CertificateDTO certificateDTO) {
        User user = userRepository.findById(certificateDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + certificateDTO.getUserId()));
        Course course = courseRepository.findById(certificateDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + certificateDTO.getCourseId()));
        Batch batch = batchRepository.findById(certificateDTO.getBatchId())
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found with ID: " + certificateDTO.getBatchId()));

        // Check for duplicate certificate
        if (certificateRepository.existsByUserAndCourse(user, course)) {
            throw new BadRequestException("Certificate already exists for user " + user.getFullName() + " and course " + course.getCourseName());
        }

        Certificate certificate = new Certificate();
        certificate.setCertificateNumber(certificateDTO.getCertificateNumber() != null && !certificateDTO.getCertificateNumber().isBlank()
                ? certificateDTO.getCertificateNumber()
                : "CERT-" + UUID.randomUUID().toString().substring(0, 8));
        certificate.setIssueDate(certificateDTO.getIssueDate());
        certificate.setUser(user);
        certificate.setCourse(course);
        certificate.setBatch(batch);

        Certificate savedCertificate = certificateRepository.save(certificate);

        // Generate PDF and send email
        byte[] pdfContent = pdfGenerationService.generateCertificatePdf(savedCertificate);
        emailService.sendCertificateEmail(
                user.getEmail(),
                pdfContent,
                savedCertificate.getCertificateNumber(),
                user.getFullName(),
                course.getCourseName()
        );

        notificationService.notifyUser(
                user.getUserId(),
                "Your certificate for " + course.getCourseName() + " (Batch: " + batch.getBatchNo() + ") has been issued"
        );

        // Manually map to DTO to avoid ambiguity
        CertificateDTO resultDTO = modelMapper.map(savedCertificate, CertificateDTO.class);
        resultDTO.setUserId(savedCertificate.getUser().getUserId());
        resultDTO.setCourseId(savedCertificate.getCourse().getCourseId());
        resultDTO.setBatchId(savedCertificate.getBatch().getBatchId());

        return resultDTO;
    }

    @Override
    public void generateCertificatesForBatch(String batchId) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found with ID: " + batchId));

        List<User> students = batch.getStudents();
        if (students == null || students.isEmpty()) {
            throw new BadRequestException("No students found in batch " + batch.getBatchNo());
        }

        for (User student : students) {
            // Check if certificate already exists for this user and course
            if (certificateRepository.existsByUserAndCourse(student, batch.getCourse())) {
                continue; // Skip if certificate already exists
            }

            CertificateDTO certificateDTO = new CertificateDTO();
            certificateDTO.setCertificateNumber("CERT-" + UUID.randomUUID().toString().substring(0, 8));
            certificateDTO.setIssueDate(LocalDate.now());
            certificateDTO.setUserId(student.getUserId());
            certificateDTO.setCourseId(batch.getCourse().getCourseId());
            certificateDTO.setBatchId(batchId);
            generateCertificate(certificateDTO);
        }

        notificationService.notifyBatchUpdate(
                batchId,
                "Certificates have been issued for batch " + batch.getBatchNo()
        );
    }

    @Override
    public void sendCertificateToUser(String certificateId) {
        Certificate certificate = certificateRepository.findById(certificateId)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate not found with ID: " + certificateId));

        logger.info("Generating PDF for certificate ID: {}", certificateId);
        logger.info("Certificate details: User={}, Course={}, Batch={}, IssueDate={}, CertificateNumber={}",
                certificate.getUser().getFullName(),
                certificate.getCourse().getCourseName(),
                certificate.getBatch().getBatchNo(),
                certificate.getIssueDate(),
                certificate.getCertificateNumber());

        byte[] pdfContent = pdfGenerationService.generateCertificatePdf(certificate);
        if (pdfContent != null && pdfContent.length > 0) {
            logger.info("PDF generated successfully for certificate ID: {}", certificateId);
            emailService.sendCertificateEmail(
                    certificate.getUser().getEmail(),
                    pdfContent,
                    certificate.getCertificateNumber(),
                    certificate.getUser().getFullName(),
                    certificate.getCourse().getCourseName()
            );
            logger.info("Certificate email sent to: {}", certificate.getUser().getEmail());
        } else {
            logger.error("Failed to generate PDF content for certificate ID: {}", certificateId);
        }
    }

    @Override
    public CertificateDTO getCertificate(String certificateId) {
        Certificate certificate = certificateRepository.findById(certificateId)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate not found with ID: " + certificateId));
        return modelMapper.map(certificate, CertificateDTO.class);
    }

    @Override
    public List<CertificateDTO> getCertificatesByUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        List<Certificate> certificates = certificateRepository.findByUser(user);

        return certificates.stream()
                .map(certificate -> {
                    CertificateDTO dto = modelMapper.map(certificate, CertificateDTO.class);
                    dto.setUserId(certificate.getUser().getUserId());
                    dto.setCourseId(certificate.getCourse().getCourseId());
                    dto.setBatchId(certificate.getBatch().getBatchId());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}