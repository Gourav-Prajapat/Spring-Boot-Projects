package com.smartcontactmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartcontactmanager.helper.Message;
import com.smartcontactmanager.helper.MessageType;
import com.smartcontactmanager.repositories.UserRepository;
import com.smartcontactmanager.services.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    
    private final UserRepository userRepository;
        
    public AuthController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    // verify email
    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token, HttpSession session) {

        return userService.getUserByToken(token)
        .map(user -> {
            if (user.getEmailToken().equals(token)) {
                user.setEmailVerified(true);
                user.setEnabled(true);
                userRepository.save(user);

                session.setAttribute("message", createSuccessMessage());
                return "success_page";
            }
            session.setAttribute("message", createErrorMessage());
            return "error_page";
        })
        .orElseGet(() -> {
            session.setAttribute("message", createErrorMessage());
            return "error_page";
        });
}

private Message createSuccessMessage() {
    return Message.builder()
        .content("Your Email is Verified, Now you can Login")
        .type(MessageType.GREEN)
        .build();
}

private Message createErrorMessage() {
    return Message.builder()
        .content("Email is not Verified! Token is not associated with User")
        .type(MessageType.RED)
        .build();
}
}




// public class AuthController {

//     @Autowired
//     private UserService userService;

//     @Autowired
//     private UserRepository userRepository;

//     // verify email
//     @GetMapping("/verify-email")
//     public String verifyEmail(@RequestParam("token") String token, HttpSession session) {

//         User user = userService.getUserByToken(token).orElse(null);

//         if (user != null) {
//             if (user.getEmailToken().equals(token)) {
//                 user.setEmailVerified(true);
//                 user.setEnabled(true);
//                 userRepository.save(user);

//                 session.setAttribute("message", Message.builder()
//                         .content("Your Email is Verified, Now you can Login")
//                         .type(MessageType.green)
//                         .build());
//                 return "success_page";
//             }
//             session.setAttribute("message", Message.builder()
//                     .content("Email is not Verified ! Token is not assosiated with User")
//                     .type(MessageType.red)
//                     .build());
//             return "error_page";
//         }
//         session.setAttribute("message", Message.builder()
//                 .content("Email is not Verified ! Token is not assosiated with User")
//                 .type(MessageType.red)
//                 .build());
//         return "error_page";
//     }

// }