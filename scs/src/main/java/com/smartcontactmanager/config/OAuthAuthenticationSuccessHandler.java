package com.smartcontactmanager.config;

import java.util.List;
import java.util.UUID;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.smartcontactmanager.entities.Providers;
import com.smartcontactmanager.entities.User;
import com.smartcontactmanager.helper.AppConstants;
import com.smartcontactmanager.repositories.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        logger.info("OAuthAuthenticationSuccessHandler");

        // indentify the provider
        var OAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String authorizedClientRegistrationId = OAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        logger.info("OAuth Provider: " + authorizedClientRegistrationId);

        var oauth2User = (DefaultOAuth2User) authentication.getPrincipal();
        oauth2User.getAttributes().forEach((key, value) -> {
            logger.info(key + " : " + value);
        });

        // Create a new User object and set common fields
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEmailVerified(true);
        user.setEnabled(true);

        if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {
            user.setEmail(oauth2User.getAttribute("email").toString());
            user.setName(oauth2User.getAttribute("name").toString());
            user.setProfilePic(oauth2User.getAttribute("picture").toString());
            user.setProviderUserID(oauth2User.getName());
            user.setProvider(Providers.GOOGLE);
            user.setAbout("This is account is created by the google");

        } else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
            String email = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString()
                    : oauth2User.getAttribute("login").toString() + "@gmail.com";

            String picture = oauth2User.getAttribute("avatar_url").toString();
            String name = oauth2User.getAttribute("login");
            String providerUserId = oauth2User.getName();

            user.setEmail(email);
            user.setProfilePic(picture);
            user.setName(name);
            user.setProviderUserID(providerUserId);
            user.setProvider(Providers.GITHUB);
            user.setAbout("This is account is created by the github");

        } else {
            logger.info("OAuthAuthenticationSuccessHandler: Unknown Provider");
        }

        // Check if the user already exists in the database
        User existingUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (existingUser == null) {
            // Save new user
            userRepository.save(user);
            logger.info("New user saved: " + user.getEmail());
        } else {
            // Update existing user with new profile picture and name
            existingUser.setProfilePic(user.getProfilePic());
            existingUser.setName(user.getName()); // Optionally update name
            userRepository.save(existingUser);
            logger.info("User updated: " + existingUser.getEmail());
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }

}
