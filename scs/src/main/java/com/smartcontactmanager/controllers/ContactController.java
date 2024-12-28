package com.smartcontactmanager.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartcontactmanager.entities.Contact;
import com.smartcontactmanager.entities.User;
import com.smartcontactmanager.forms.ContactForm;
import com.smartcontactmanager.forms.ContactSearchform;
import com.smartcontactmanager.helper.AppConstants;
import com.smartcontactmanager.helper.Helper;
import com.smartcontactmanager.helper.Message;
import com.smartcontactmanager.helper.MessageType;
import com.smartcontactmanager.services.ContactService;
import com.smartcontactmanager.services.ImageService;
import com.smartcontactmanager.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private final ContactService contactService;
    private final UserService userService;
    private final ImageService imageService;

    public ContactController(ContactService contactService, UserService userService, ImageService imageService) {
        this.contactService = contactService;
        this.userService = userService;
        this.imageService = imageService;
    }

    Logger logger = LoggerFactory.getLogger(ContactController.class);

    @RequestMapping("/add")
    public String addContactView(Model model) {

        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);

        return "user/add_contact";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,
            Authentication authentication, HttpSession session) {

        // process the form data
        // validate the form
        if (result.hasErrors()) {
            session.setAttribute("message", Message.builder().content("Please Correct the Following errors")
                    .type(MessageType.RED)
                    .build());
            return "user/add_contact";
        }

        // current user
        String username = Helper.getEmailOfLoggedInUser(authentication);

        // form --> contact
        User user = userService.getUserByEmail(username);

        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setFavorite(contactForm.isFavorite());
        contact.setDescription(contactForm.getDescription());
        contact.setAddress(contactForm.getAddress());
        contact.setInstagramLink(contactForm.getInstagramLink());
        contact.setLinkedlnLink(contactForm.getLinkedInLink());
        contact.setUser(user);

        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            // process the contact picture
            String filename = UUID.randomUUID().toString();
            String FileURL = imageService.uploadImage(contactForm.getContactImage(), filename);

            contact.setContactImage(FileURL);
            contact.setCloudinaryImagePublicId(filename);
        }

        contactService.save(contact);
        System.out.println(contactForm);

        // set the message to be displayed on view
        session.setAttribute("message",
                Message.builder().content("Your Contact is Saved Successfully").type(MessageType.GREEN).build());

        return "redirect:/user/contacts/add";
    }

    @RequestMapping
    public String viewContacts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction, Authentication authentication,
            Model model) {

        // load all the user contacts
        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(username);

        Page<Contact> pageContact = contactService.getByUser(user, page, size, sortBy, direction);

        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("pageContact", pageContact);

        model.addAttribute("contactSearchform", new ContactSearchform());
        return "user/contacts";
    }

    // search handler
    @RequestMapping("/search")
    public String searchHandler(
            @ModelAttribute ContactSearchform contactSearchform,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model, Authentication authentication) {

        logger.info("field {} keyword {}", contactSearchform.getField(), contactSearchform.getValue());
        var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        Page<Contact> pageContact = null;
        if (contactSearchform.getField().equalsIgnoreCase("name")) {
            pageContact = contactService.searchByName(contactSearchform.getValue(), size, page, sortBy, direction,
                    user);

        } else if (contactSearchform.getField().equalsIgnoreCase("email")) {
            pageContact = contactService.searchByEmail(contactSearchform.getValue(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchform.getField().equalsIgnoreCase("phoneNumber")) {
            pageContact = contactService.searchByPhoneNumber(contactSearchform.getValue(), size, page, sortBy,
                    direction, user);
        }

        logger.info("pageContact {}", pageContact);

        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("ContactSearchform", contactSearchform);
        model.addAttribute("pageContact", pageContact);

        return "user/search";
    }

    // delete contact
    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable String contactId, HttpSession session) {

        contactService.delete(contactId);
        logger.info("contact{} deleted", contactId);

        session.setAttribute("message",
                Message.builder().content("Contact is Deleted Successfully!")
                        .type(MessageType.GREEN).build());

        return "redirect:/user/contacts";
    }

    // update contact form view
    @GetMapping("/view/{contactId}")
    public String updateContactFormView(@PathVariable("contactId") String contactId, Model model) {
        var contact = contactService.getById(contactId);

        ContactForm contactForm = new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setInstagramLink(contact.getInstagramLink());
        contactForm.setLinkedInLink(contact.getLinkedlnLink());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setPicture(contact.getContactImage());

        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", contactId);

        return "user/update_contact_view";
    }

    @RequestMapping(value = "/update/{contactId}", method = RequestMethod.POST)
    public String updateContact(@PathVariable("contactId") String contactId,
            @Valid @ModelAttribute ContactForm contactForm, BindingResult bindingResult, Model model,
            HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "user/update_contact_view";
        }

        // update contact
        var con = contactService.getById(contactId);
        con.setId(contactId);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setInstagramLink(contactForm.getInstagramLink());
        con.setLinkedlnLink(contactForm.getLinkedInLink());
        con.setFavorite(contactForm.isFavorite());

        // image process
        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            logger.info("file is not empty");
            String fileName = UUID.randomUUID().toString();
            String imageUrl = imageService.uploadImage(contactForm.getContactImage(), fileName);
            con.setCloudinaryImagePublicId(fileName);
            con.setContactImage(imageUrl);
            contactForm.setPicture(imageUrl);
        } else {
            logger.info("file is empty");
        }

        var updatedContact = contactService.update(con);
        logger.info("Updated Contact {}", updatedContact);

        session.setAttribute("message", Message.builder()
                .content("Your Contact is updated successfully!")
                .type(MessageType.GREEN)
                .build());

        return "redirect:/user/contacts/view/" + contactId;

    }
}
