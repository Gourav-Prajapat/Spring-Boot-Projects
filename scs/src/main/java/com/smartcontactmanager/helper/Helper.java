package com.smartcontactmanager.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication){
        
        if(authentication instanceof OAuth2AuthenticationToken){
            
           var OAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
           var clientID = OAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
           var oauth2User = (OAuth2User)authentication.getPrincipal();
           String username = "";

           if(clientID.equalsIgnoreCase("google")){
            System.out.println("Getting Email form Google");

            username = oauth2User.getAttribute("email").toString();

           }
           else if(clientID.equalsIgnoreCase("github")){
            System.out.println("Getting Email from Github");
            username = oauth2User.getAttribute("email") !=null ? oauth2User.getAttribute("email").toString() : oauth2User.getAttribute("login").toString() +"@gmail.com";
           }
            
            return username;
        }
        else{
            System.out.println("Getting data from local database");
            return authentication.getName();
        }

    }

    public static String getLinkForEmailVerification(String emailToken){

        String link = "http://localhost:8080/auth/verify-email?token=" + emailToken;
        
        return link;
    }
}
