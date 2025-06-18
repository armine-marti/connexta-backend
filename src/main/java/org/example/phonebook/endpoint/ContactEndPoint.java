package org.example.phonebook.endpoint;

import lombok.RequiredArgsConstructor;
import org.example.phonebook.dto.contact.ContactPageResponse;
import org.example.phonebook.dto.contact.ContactResponseDto;
import org.example.phonebook.dto.contact.SaveContactRequest;
import org.example.phonebook.entity.Contact;
import org.example.phonebook.security.CurrentUser;
import org.example.phonebook.service.ContactService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
/**
 * REST controller for managing contacts.
 *
 * <p>Provides endpoints to create, retrieve, update, and delete contacts
 * associated with the authenticated user.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/contacts")
public class ContactEndPoint {


    private final ContactService contactService;


    @GetMapping
    public ResponseEntity<ContactPageResponse> getAll(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "1000") int pageSize,
            @RequestParam(value = "search", required = false) String search) {

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);

        Page<ContactResponseDto> page = contactService.findContactsById(
                currentUser.getUser().getId(),
                pageRequest,
                search
        );

        ContactPageResponse response = new ContactPageResponse(page.getContent(), page.getTotalElements());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContactById(@PathVariable("id") int id, @AuthenticationPrincipal CurrentUser currentUser)
    {
        Optional<ContactResponseDto> contactOpt = contactService.findByIdAndUser(id, currentUser.getUser());

        if (contactOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contact not found or access denied");
        }

        return ResponseEntity.ok(contactOpt.get());
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SaveContactRequest saveContactRequest,
                                    @AuthenticationPrincipal CurrentUser currentUser) {

        saveContactRequest.setUser(currentUser.getUser());

        if (contactService.findByPhoneAndUser(saveContactRequest.getPhone(), currentUser.getUser()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Contact with this phone number already exists for this user.");
        }
        ContactResponseDto contactFromDb = contactService.save(saveContactRequest);

        return ResponseEntity.ok(contactFromDb);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateContact(@PathVariable int id, @RequestBody ContactResponseDto contactDto,
                                           @AuthenticationPrincipal CurrentUser currentUser) {
        Optional<Contact> existingContact = contactService.findByPhoneAndUser(contactDto.getPhone(), currentUser.getUser());

        if (existingContact.isPresent() && existingContact.get().getId() != id) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Contact with this phone already exists for this user.");
        }
        ContactResponseDto contactResponseDto = contactService.updateContact(id, contactDto);
        return ResponseEntity.ok(contactResponseDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable("id") int id) {
        contactService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
