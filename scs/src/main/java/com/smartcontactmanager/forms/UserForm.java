package com.smartcontactmanager.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserForm {
    @NotBlank(message = "User name is Required")
    @Size(min = 3 ,message = "Minimum 3 Character is Required")
    private String name;

    @NotBlank(message = "Email is Required")
    @Email(message = "Invalid Email Address")
    private String email;

    @NotBlank(message = "Password is Required")
    @Size(min = 6, message = "Minimum 6 Character is Required")
    private String password;

    @Size(min = 10,max = 12, message = "Invalid Phone number")
    private String phoneNumber;

    @NotBlank(message = "About is Required")
    private String about;
}
