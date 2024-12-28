package com.smartcontactmanager.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.smartcontactmanager.entities.Contact;
import com.smartcontactmanager.entities.User;

public interface ContactService {
    //saveContact
    Contact save(Contact contact);

    //updateContact
    Contact update(Contact contact);

    //get Contact by id
    Contact getById(String id);

    //deleteContact
    void delete(String id);

    //get contact
    List<Contact>getAll();

    //search Contact by name
    Page<Contact> searchByName (String nameKeyword, int size, int page, String sortBy, String order, User user);

    //search Contact by phone
    Page<Contact> searchByPhoneNumber (String phoneNumberKeyword, int size, int page, String sortBy, String order, User user);

    //search Contact by email
    Page<Contact> searchByEmail (String emailKeyword, int size, int page, String sortBy, String order, User user);

    //get contact by userId
    List<Contact> getByUserId(String userId);

    //get contact by user
    Page<Contact> getByUser(User user, int page, int size, String sortField, String direction);

}
