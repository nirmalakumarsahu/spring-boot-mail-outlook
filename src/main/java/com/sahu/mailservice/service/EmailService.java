package com.sahu.mailservice.service;

import com.sahu.mailservice.dto.EmailRequest;
import org.springframework.web.multipart.MultipartFile;

public interface EmailService {

    String sendEmail(EmailRequest emailRequest, MultipartFile[] files);
}
