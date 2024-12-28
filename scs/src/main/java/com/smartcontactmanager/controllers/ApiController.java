package com.smartcontactmanager.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartcontactmanager.entities.Contact;
import com.smartcontactmanager.services.ContactService;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api")
public class ApiController {

    private final ContactService contactService;

    public ApiController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contacts/{contactId}")
    public ResponseEntity<Contact> getContact(@PathVariable String contactId) {
        Contact contact = contactService.getById(contactId);
        
        if (contact != null) {
            return ResponseEntity.ok(contact);
        }
        return ResponseEntity.notFound().build();
    }
}




// @RestController @RequestMapping("/api") public class ApiController {      @Autowired     private ContactService contactService;      @GetMapping("/contacts/{contactId}")     public Contact getContact(@PathVariable String contactId){         return contactService.getById(contactId);     } }
