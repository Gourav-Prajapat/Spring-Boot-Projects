package com.smartcontactmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.smartcontactmanager.entities.User;
import com.smartcontactmanager.forms.UserForm;
import com.smartcontactmanager.helper.Message;
import com.smartcontactmanager.helper.MessageType;
import com.smartcontactmanager.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
public class PageController {
    
    @Autowired
    private UserService userService;


    @RequestMapping("/home")
    public String home(){
        System.out.println("home controller is invoked");
        return "home";
    }
    
    @RequestMapping("/services")
    public String services(){
        System.out.println("services controller is invoked");
        return "services";
    } 

    @RequestMapping("/about")
    public String about(){
        System.out.println("about controller is invoked");
        return "about";
    } 

    @RequestMapping("/contact")
    public String contact(){
        System.out.println("contact controller is invoked");
        return "contact";
    }
    
    @GetMapping("/login")
    public String login() {
        System.out.println("login controller is invoked");
        return "login";
    }
    
    @GetMapping("/signup")
    public String signup(Model model) {
        System.out.println("signup controller is invoked");
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "signup";
    }

    @RequestMapping(value = "/do-signUp", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm,BindingResult bindingResult ,HttpSession session) {
       System.out.println("processing registration");
       // fetch form data
       System.out.println(userForm);
       // validate form data
       if (bindingResult.hasErrors()) {
        System.out.println(bindingResult.getAllErrors());
        return "signup";
    }


       // save data to database, will use UserService, we are getting userform from this we will user
    //    User user = User.builder()
    //    .name(userform.getName())
    //    .email(userform.getEmail())
    //    .password(userform.getPassword())
    //    .about(userform.getAbout())
    //    .phoneNumber(userform.getPhoneNumber())
    //    .profilePic("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.freepik.com%2Fpremium-ai-image%2Fcartoon-girl-with-hat-snowman-her-back_354324731.htm&psig=AOvVaw2Ho_BVZatQnrU-9yapultb&ust=1727961892987000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCLD-ofPl74gDFQAAAAAdAAAAABAJ")
    //    .build();

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.freepik.com%2Fpremium-ai-image%2Fcartoon-girl-with-hat-snowman-her-back_354324731.htm&psig=AOvVaw2Ho_BVZatQnrU-9yapultb&ust=1727961892987000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCLD-ofPl74gDFQAAAAAdAAAAABAJ");
       
        User savedUser = userService.saveUser(user);
        System.out.println("user saved: ");
       
        //message = "successfull registration"
        Message message = Message.builder().content("Registration Successful").type(MessageType.GREEN).build();
        session.setAttribute("message",message);
    
        return "redirect:/signup";
    } 
}