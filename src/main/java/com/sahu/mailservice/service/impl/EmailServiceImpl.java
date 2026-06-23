package com.sahu.mailservice.service.impl;

import com.sahu.mailservice.dto.EmailRequest;
import com.sahu.mailservice.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public String sendEmail(EmailRequest emailRequest, MultipartFile[] files) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(emailRequest.to().toArray(String[]::new));

            if (Objects.nonNull(emailRequest.cc())) {
                helper.setCc(emailRequest.cc().toArray(String[]::new));
            }
            if (Objects.nonNull(emailRequest.bcc())) {
                helper.setBcc(emailRequest.bcc().toArray(String[]::new));
            }

            helper.setSubject(emailRequest.subject());
            helper.setText(emailRequest.body(), emailRequest.html());

            if (emailRequest.sendDate()) {
                helper.setSentDate(new Date());
            }

            if (Objects.nonNull(files)) {
                for (MultipartFile file : files) {
                    helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), file);
                }
            }

            javaMailSender.send(message);
            return "Email sent successfully to " + String.join(", ", emailRequest.to());
        }
        catch (Exception exception) {
            throw new RuntimeException("Error while sending email: " + exception.getMessage());
        }
    }

}
