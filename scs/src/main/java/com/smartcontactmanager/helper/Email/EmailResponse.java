package com.smartcontactmanager.helper.Email;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class EmailResponse {

    private String message;
    private HttpStatus httpStatus;
    
    @Builder.Default
    private boolean success = false;

}
