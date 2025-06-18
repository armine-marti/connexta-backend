package org.example.phonebook.repository;

import org.example.phonebook.entity.Contact;
import org.example.phonebook.entity.User;
import org.example.phonebook.enums.ContactStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
/**
 * Repository interface for {@link Contact} entity operations.
 *
 * <p>Extends {@link JpaRepository} to provide CRUD operations and includes custom query methods
 * for finding contacts by phone, email, user association, and status. Supports pagination and
 * soft deletion.
 */
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    Optional<Contact> findByPhone(String phone);

    Optional<Contact> findByIdAndUser(int id, User user);

    Optional<Contact> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE Contact c SET c.contactStatus = 'DELETED' WHERE c.id = :contactId")
    void softDeleteById(@Param("contactId") int contactId);


    Optional<Contact> findByPhoneAndUser(String phone, User user);

    Page<Contact> findAllByUserIdAndContactStatus(int userId, ContactStatus contactStatus, Pageable pageable);

    Page<Contact> findByUserIdAndContactStatusAndNameContainingIgnoreCase(
            int userId, ContactStatus contactStatus, String name, Pageable pageable);

}
