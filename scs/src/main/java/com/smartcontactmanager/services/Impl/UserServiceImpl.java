package com.smartcontactmanager.services.Impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartcontactmanager.entities.User;
import com.smartcontactmanager.helper.AppConstants;
import com.smartcontactmanager.helper.Helper;
import com.smartcontactmanager.helper.ResourceNotFoundException;
import com.smartcontactmanager.repositories.UserRepository;
import com.smartcontactmanager.services.EmailService;
import com.smartcontactmanager.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    
    @Override
    public User saveUser(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        
        //password encode
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //Set the role user
        user.setRoleList(List.of(AppConstants.ROLE_USER));

        logger.info(user.getProvider().toString());

        String emailToken = UUID.randomUUID().toString();
        user.setEmailToken(emailToken);
        User savedUser = userRepository.save(user);
        
        String link = Helper.getLinkForEmailVerification(emailToken);
        emailService.sendEmail(savedUser.getEmail(), "Account Verification : Smart contact Manager", "Plz click the link to verify Your account: "+ link);

        return savedUser;
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
       User user2 = userRepository.findById(user.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    //    will update user2 form user
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setAbout(user.getAbout());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserID(user.getProviderUserID());
    
        // save the user in database
        User savedUser = userRepository.save(user2);

        return  Optional.ofNullable(savedUser);
    }

    @Override
    public void deleteUser(String id) {
        User user2 = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(user2);
    }

    @Override
    public boolean isUserExist(String userId) {
        User user2 = userRepository.findById(userId).orElse(null);
        return user2 !=null ? true : false;
    }

    @Override
    public boolean isUserExistByemail(String email) {
        User user2 = userRepository.findByEmail(email).orElse(null);
        return user2 !=null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Optional<User> getUserByToken(String emailToken) {
        return userRepository.findByEmailToken(emailToken);
    }
}