package com.smartcontactmanager.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.MessagingException;

public interface EmailService {

    //sendEmail to single person
    void sendEmail(String to, String subject, String body);

    //sendEmail to multiple person
    void sendEmail(String []to, String subject, String body);

    //sendEmail With Html
    void sendEmailWithHtml(String to, String subject, String htmlContent);

    //sendEmail With Attachment
    // void sendEmailWithFile(String to, String subject, String body, File file);

    //sendEmail through input stream
    // void sendEmailWithFile(String to, String subject, String body, InputStream inputStream, String filename) throws MessagingException;

    void sendEmailWithFile(String to, String subject, String body, List<MultipartFile> files) throws MessagingException;


}
