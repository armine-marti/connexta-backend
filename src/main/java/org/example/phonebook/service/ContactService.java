package org.example.phonebook.service;

import org.example.phonebook.dto.contact.ContactResponseDto;
import org.example.phonebook.dto.contact.SaveContactRequest;
import org.example.phonebook.entity.Contact;
import org.example.phonebook.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
/**
 * Service interface for managing {@link Contact} entities.
 *
 * <p>Defines the contract for CRUD operations, search, and retrieval of contacts,
 * including user-specific filtering and pagination.
 */
public interface ContactService {


    List<ContactResponseDto> findAll();

    ContactResponseDto save(SaveContactRequest contactRequest);

    Optional<Contact> findByEmail(String email);

    ContactResponseDto findById(int id);

    ContactResponseDto updateContact(int id, ContactResponseDto contactDto);

    void deleteById(int id);

    Optional<Contact> findByPhone(String phone);

    Page<ContactResponseDto> findContactsById(int userId, Pageable pageable, String search);

    Optional<Contact> findByPhoneAndUser(String phone, User user);

    Optional<ContactResponseDto> findByIdAndUser(int id, User user);
}
