package com.sahu.mailservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record EmailRequest(
        @NotEmpty(message = "At least one recipient is required")
        List<@Email(message = "Invalid email address") String> to,
        List<@Email(message = "Invalid CC email") String> cc,
        List<@Email(message = "Invalid BCC email") String> bcc,
        @NotBlank(message = "Subject is required")
        String subject,
        @NotBlank(message = "Body is required")
        String body,
        boolean html,
        boolean sendDate
) {
}
