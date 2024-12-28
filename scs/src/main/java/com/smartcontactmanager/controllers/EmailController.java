package com.smartcontactmanager.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smartcontactmanager.helper.Email.EmailRequest;
import com.smartcontactmanager.helper.Email.EmailResponse;
import com.smartcontactmanager.services.EmailService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/email")
public class EmailController {

    private EmailService emailService;

    Logger logger = LoggerFactory.getLogger(EmailController.class);

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }


    @PostMapping("/send-text")
    public ResponseEntity<?> sendTextEmail(@RequestBody EmailRequest request) {
        // Validate the "To" address
        if (request.getTo() == null || request.getTo().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(EmailResponse.builder()
                            .message("To address must not be null or empty")
                            .httpStatus(HttpStatus.BAD_REQUEST)
                            .success(false)
                            .build());
        }

        try {
            emailService.sendEmail(request.getTo(), request.getSubject(), request.getBody());

            return ResponseEntity.ok(
                    EmailResponse.builder()
                            .message("Plain text email sent successfully!")
                            .httpStatus(HttpStatus.OK)
                            .success(true)
                            .build());
        } catch (Exception e) {
            logger.error("Error while sending plain text email: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(EmailResponse.builder()
                            .message("Failed to send plain text email: " + e.getMessage())
                            .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                            .success(false)
                            .build());
        }
    }

    @PostMapping("/send-with-attachments")
    public ResponseEntity<?> sendEmailWithFile(@RequestPart("request") EmailRequest request,
            @RequestParam("files") List<MultipartFile> files) {
        try {
            // Call email service to send email
            emailService.sendEmailWithFile(request.getTo(), request.getSubject(), request.getBody(), files);

            // Return success response
            return ResponseEntity.ok(
                    EmailResponse.builder()
                            .message("Email sent successfully with attachments!")
                            .httpStatus(HttpStatus.OK)
                            .success(true)
                            .build());
        } catch (Exception e) {
            // Return error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            EmailResponse.builder()
                                    .message("Failed to send email: " + e.getMessage())
                                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .success(false)
                                    .build());
        }
    }

    // @PostMapping("/send-with-html")
    // public ResponseEntity<?> sendEmailWithHtml(@RequestBody EmailRequest request)
    // {
    // if (request.getTo() == null || request.getTo().trim().isEmpty()) {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    // .body(EmailResponse.builder()
    // .message("To address must not be null or empty")
    // .httpStatus(HttpStatus.BAD_REQUEST)
    // .success(false)
    // .build());
    // }
    // try {
    // emailService.sendEmailWithHtml(request.getTo(), request.getSubject(),
    // request.getBody());
    // return ResponseEntity.ok(
    // EmailResponse.builder()
    // .message("Email sent successfully!")
    // .httpStatus(HttpStatus.OK)
    // .success(true)
    // .build());
    // } catch (Exception e) {
    // // Log the error details
    // logger.error("Error while sending email: ", e);
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body(EmailResponse.builder()
    // .message("Failed to send email: " + e.getMessage())
    // .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    // .success(false)
    // .build());
    // }
    // }

    // email with file (inputstream)
    // @PostMapping("/send-with-file")
    // public ResponseEntity<?> sendWithFile(@RequestPart EmailRequest request,
    // @RequestPart MultipartFile file)
    // throws IOException {
    // try {
    // emailService.sendEmailWithFile(
    // request.getTo(),
    // request.getSubject(),
    // request.getBody(),
    // file.getInputStream(),
    // file.getOriginalFilename());

    // return ResponseEntity.ok(EmailResponse.builder()
    // .message("Email sent successfully!")
    // .httpStatus(HttpStatus.OK)
    // .success(true)
    // .build());

    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body(EmailResponse.builder()
    // .message("Failed to send email: " + e.getMessage())
    // .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    // .success(false)
    // .build());
    // }
    // }

    // @GetMapping("/current-user")
    // public ResponseEntity<String> getCurrentUserEmail(Authentication
    // authentication) {

    // String email = Helper.getEmailOfLoggedInUser(authentication);
    // logger.info("current logged in user : {}" , email);

    // if (email != null) {
    // return ResponseEntity.ok(email);
    // }
    // return ResponseEntity.status(401).body("User not authenticated");
    // }
}
