package com.smartcontactmanager.services.Impl;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smartcontactmanager.services.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender;

    private Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public static String stripHtmlTagsWithJsoup(String input) {
        // Use Jsoup to clean HTML and decode entities
        Document doc = Jsoup.parse(input);
        String textWithoutHtml = doc.text(); // Extract text only (HTML tags are removed)
        return textWithoutHtml;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        // SimpleMailMessage message = new SimpleMailMessage();
        // message.setTo(to);
        // message.setSubject(subject);
        // message.setText(body);
        // message.setFrom("gprajapati644@gmail.com");

        // javaEmailSender.send(message);
        // logger.info("An Email has been sent");
        String plainBody = stripHtmlTagsWithJsoup(body);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_NO, StandardCharsets.UTF_8.name());

            helper.setTo(to);
            helper.setSubject(subject);
            // Set the content type as plain text
            message.setContent(plainBody, "text/plain; charset=UTF-8");

            // helper.setText(body, false); // false indicates plain text (if true, it's
            // HTML)
            System.out.println("Sending email body: " + plainBody);
            javaMailSender.send(message);
            System.out.println("Sending email body: " + body);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendEmail(String[] to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("gprajapati644@gmail.com");

        javaMailSender.send(message);
        logger.info("An Email has been sent");
    }

    @Override
    public void sendEmailWithHtml(String to, String subject, String htmlContent) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            // if (to == null || to.trim().isEmpty()) {
            // logger.error("Email 'to' address is missing or empty. Cannot send email.");
            // return; // Exit the method if the 'to' address is not provided
            // }

            message.setTo(to);
            message.setSubject(subject);
            message.setText(htmlContent, true);
            message.setFrom("gprajapati644@gmail.com");
            javaMailSender.send(mimeMessage);
            logger.info("An Email has been sent");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /* 
      @Override
      public void sendEmailWithFile(String to, String subject, String body, File
      file) {
      
      MimeMessage mimeMessage = javaEmailSender.createMimeMessage();
      
      MimeMessageHelper message;
      try {
      message = new MimeMessageHelper(mimeMessage, true);
      message.setTo(to);
      message.setSubject(subject);
      message.setText(body);
      
      FileSystemResource fileSystemResource = new FileSystemResource(file);
      message.addAttachment(fileSystemResource.getFilename(), file);
      message.setFrom("gprajapati644@gmail.com");
      javaEmailSender.send(mimeMessage);
      logger.info("An Email has been sent succesfully");
      } catch (MessagingException e) {
      e.printStackTrace();
      }
      }
      
      @Override
      public void sendEmailWithFile(String to, String subject, String body,
      InputStream inputStream, String filename) throws MessagingException {
      MimeMessage mimeMessage = javaEmailSender.createMimeMessage();
      
      try (inputStream) {
      MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
      message.setFrom("gprajapati644@gmail.com");
      message.setTo(to);
      message.setSubject(subject);
      message.setText(body);
      
      // Create temporary file
      Path tempFile = Files.createTempFile("email-attachment-", filename);
      try {
      // Copy input stream to temporary file
      Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
      
      // Add attachment
      FileSystemResource fileResource = new FileSystemResource(tempFile.toFile());
      message.addAttachment(filename, fileResource);
      
      // Send email
      javaMailSender.send(mimeMessage);
      logger.info("Email sent successfully with attachment: {}", filename);
      
      } finally {
      // Clean up temporary file
      try {
      Files.deleteIfExists(tempFile);
      } catch (IOException e) {
      logger.warn("Failed to delete temporary file: {}", tempFile, e);
      }
      }
      } catch (IOException e) {
      throw new MessagingException("Failed to process attachment", e);
      }
      }
     */

    @Override
    public void sendEmailWithFile(String to, String subject, String body, List<MultipartFile> files)
            throws MessagingException {

            String plainBody = stripHtmlTagsWithJsoup(body);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(plainBody);

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                // Use the original filename from the uploaded file
                String filename = file.getOriginalFilename();

                // Add attachment directly from the MultipartFile input stream
                message.addAttachment(filename, file);
            }
        }

        javaMailSender.send(mimeMessage);
        logger.info("Email sent successfully with multiple attachments");
    }

}
