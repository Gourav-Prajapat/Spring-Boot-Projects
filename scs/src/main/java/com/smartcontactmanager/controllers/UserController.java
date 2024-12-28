package com.smartcontactmanager.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartcontactmanager.helper.Helper;

@Controller
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/dashboard")
    public String userDashboard() {
        System.out.println("user controllers is invoked");
        return "user/dashboard";
    }

    @RequestMapping("/profile")
    public String userProfile(Authentication authentication) {
        String username = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("user logged in : {}" , username);
        return "user/profile";
    }
    
    @RequestMapping("/send-email_page")
    public String renderEmailPage(Authentication authentication) {
    // Add any necessary model attributes
    String username = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("user logged in : {}" , username);
    return "user/email";
}
}
