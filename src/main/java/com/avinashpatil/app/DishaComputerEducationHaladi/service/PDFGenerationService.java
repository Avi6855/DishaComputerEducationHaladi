package com.avinashpatil.app.DishaComputerEducationHaladi.service;

import com.avinashpatil.app.DishaComputerEducationHaladi.entity.Certificate;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class PDFGenerationService {

    private static final Logger logger = LoggerFactory.getLogger(PDFGenerationService.class);
    private static final String CERTIFICATE_TEMPLATE_PATH = "templates/certificate_template.pdf";

    public byte[] generateCertificatePdf(Certificate certificate) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // Load the template as a classpath resource
            InputStream templateInputStream = getClass().getClassLoader().getResourceAsStream(CERTIFICATE_TEMPLATE_PATH);
            if (templateInputStream == null) {
                logger.error("Certificate template not found at: {}", CERTIFICATE_TEMPLATE_PATH);
                return null;
            }
            PdfReader reader = new PdfReader(templateInputStream);
            PdfStamper stamper = new PdfStamper(reader, baos);

            AcroFields form = stamper.getAcroFields();
            form.setField("FullName", certificate.getUser().getFullName());
            form.setField("Course_Name", certificate.getCourse().getCourseName());
            form.setField("Batch_No", certificate.getBatch().getBatchNo());
            form.setField("Issued_Date", certificate.getIssueDate().toString());
            form.setField("Cert_No", certificate.getCertificateNumber());

            stamper.setFormFlattening(true); // Make fields non-editable
            stamper.close();
            reader.close();

            return baos.toByteArray();
        } catch (IOException | DocumentException e) {
            logger.error("Failed to generate certificate PDF", e);
            return null;
        }
    }
}