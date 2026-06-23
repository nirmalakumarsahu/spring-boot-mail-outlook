package com.sahu.mailservice.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sahu.mailservice.dto.EmailRequest;
import com.sahu.mailservice.service.EmailService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailRestController {

    private final EmailService emailService;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    @PostMapping(path = "/send", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> sendEmail(
            @RequestPart String emailRequest,
            @RequestPart(value = "files", required = false) MultipartFile[] files) throws JsonProcessingException {
        log.info("Received email send request: {}", emailRequest);

        EmailRequest request = objectMapper.readValue(emailRequest, EmailRequest.class);
        Set<ConstraintViolation<EmailRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        String result = emailService.sendEmail(request, files);
        log.info("Email send result: {}", result);
        return ResponseEntity.ok(result);
    }

}
