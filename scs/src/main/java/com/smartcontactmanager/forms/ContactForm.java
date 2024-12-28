package com.smartcontactmanager.forms;

import org.springframework.web.multipart.MultipartFile;

import com.smartcontactmanager.Validators.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactForm {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email address [example@gmail.com]")
    private String email;

    @NotBlank(message = "Phone Number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone Number")
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    private String address;
    private String description;
    private String instagramLink;
    private String linkedInLink;
    private boolean favorite;
    
    //annotation create karenge jo file validate
    // size
    // resolution
    @ValidFile(message = "Invalid File")
    private MultipartFile contactImage;

    private String picture;
}
