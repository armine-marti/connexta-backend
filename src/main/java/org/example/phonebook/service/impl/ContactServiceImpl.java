package org.example.phonebook.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.phonebook.dto.contact.ContactResponseDto;
import org.example.phonebook.dto.contact.SaveContactRequest;
import org.example.phonebook.entity.Contact;
import org.example.phonebook.entity.User;
import org.example.phonebook.enums.ContactStatus;
import org.example.phonebook.exception.ContactNotFoundException;
import org.example.phonebook.mapper.ContactMapper;
import org.example.phonebook.repository.ContactRepository;
import org.example.phonebook.service.ContactService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Implementation of the {@link ContactService} interface.
 * <p>
 * Provides business logic for managing {@link Contact} entities including CRUD operations,
 * soft deletes, user-specific filtering, and search functionality.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {


    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    @Override
    public List<ContactResponseDto> findAll() {
        return contactMapper.toContactResponserDtoList(contactRepository.findAll());
    }


    @Override
    public ContactResponseDto save(SaveContactRequest contactRequest) {
        contactRequest.setContactStatus(ContactStatus.ACTIVE);
        Contact contact = contactRepository.save(contactMapper.toEntity(contactRequest));
        return contactMapper.toContactResponseDto(contact);
    }

    @Override
    public Optional<Contact> findByEmail(String email) {
        return contactRepository.findByEmail(email);
    }

    @Override
    public ContactResponseDto updateContact(int id, ContactResponseDto contactDto) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found with id: " + id));

        contactMapper.updateContactFromDto(contactDto, contact);

        Contact updatedContact = contactRepository.save(contact);

        return contactMapper.toContactResponseDto(updatedContact);
    }

    @Override
    public ContactResponseDto findById(int id) {
        Contact contact = contactRepository
                .findById(id)
                .orElse(null);
        if (contact == null) {
            return null;
        }
        return contactMapper.toContactResponseDto(contact);
    }

    @Override
    public void deleteById(int id) {
        if (!contactRepository.existsById(id)) {
            throw new ContactNotFoundException("Contact not found with " + id + " id");
        }
        contactRepository.softDeleteById(id);
    }

    @Override
    public Optional<Contact> findByPhone(String phone) {
        try {
            return contactRepository.findByPhone(phone);
        } catch (DataAccessException ex) {

            throw new ContactNotFoundException("Problem with finding the number in the database", ex);
        }
    }

    @Override
    public Page<ContactResponseDto> findContactsById(int userId, Pageable pageable, String search) {
        Page<Contact> contacts;

        if (search != null && !search.trim().isEmpty()) {
            contacts = contactRepository.findByUserIdAndContactStatusAndNameContainingIgnoreCase(
                    userId, ContactStatus.ACTIVE, search, pageable);
        } else {
            contacts = contactRepository.findAllByUserIdAndContactStatus(userId, ContactStatus.ACTIVE, pageable);
        }
        return contacts.map(contactMapper::toContactResponseDto);
    }


    @Override
    public Optional<Contact> findByPhoneAndUser(String phone, User user) {
        return contactRepository.findByPhoneAndUser(phone, user);
    }

    @Override
    public Optional<ContactResponseDto> findByIdAndUser(int id, User user) {

        return contactRepository.findByIdAndUser(id, user)
                .map(contactMapper::toContactResponseDto);

    }

}
