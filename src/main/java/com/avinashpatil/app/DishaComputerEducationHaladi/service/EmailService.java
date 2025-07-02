package com.avinashpatil.app.DishaComputerEducationHaladi.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendCertificateEmail(String toEmail, byte[] pdfContent, String certificateNumber, String fullName, String courseName) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("prathameshp9922@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Your Certificate - " + certificateNumber);

            String htmlContent = """
                <html>
                <body style="font-family: Arial, sans-serif; color: #333;">
                    <div style="max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 5px; background-color: #f9f9f9;">
                        <h2 style="color: #2c3e50; text-align: center;">Congratulations, %s!</h2>
                        <p style="font-size: 16px; line-height: 1.5;">
                            We are pleased to inform you that you have successfully completed the <strong>%s</strong> course. 
                            Your certificate (No: <strong>%s</strong>) is attached below.
                        </p>
                        <p style="font-size: 14px; color: #7f8c8d;">
                            This certificate is a testament to your hard work and dedication. Keep up the great work!
                        </p>
                        <div style="text-align: center; margin: 20px 0;">
                            <a href="https://dishacomputer.com" style="background-color: #3498db; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;">Visit Our Website</a>
                        </div>
                        <p style="font-size: 12px; color: #95a5a6; text-align: center;">
                            Regards,<br>Disha Computer Education Team
                        </p>
                    </div>
                </body>
                </html>
                """.formatted(fullName, courseName, certificateNumber);

            helper.setText(htmlContent, true);
            helper.addAttachment(certificateNumber + ".pdf", new ByteArrayResource(pdfContent));
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send certificate email", e);
        }
    }

    public void sendLoginAlertEmail(String toEmail, String userEmail, String deviceId) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("prathameshp9922@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Security Alert: Multiple Login Attempt");

            String htmlContent = """
                <html>
                <body style="font-family: Arial, sans-serif; color: #333;">
                    <div style="max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 5px; background-color: #f9f9f9;">
                        <h2 style="color: #e74c3c; text-align: center;">Security Alert</h2>
                        <p style="font-size: 16px; line-height: 1.5;">
                            A login attempt was detected for user <strong>%s</strong> from device ID: <strong>%s</strong>.
                            The user is already logged in from another device.
                        </p>
                        <p style="font-size: 14px; color: #7f8c8d;">
                            If this was not authorized, please take appropriate action.
                        </p>
                        <p style="font-size: 12px; color: #95a5a6; text-align: center;">
                            Regards,<br>Disha Computer Education Team
                        </p>
                    </div>
                </body>
                </html>
                """.formatted(userEmail, deviceId);

            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send login alert email", e);
        }
    }
}
