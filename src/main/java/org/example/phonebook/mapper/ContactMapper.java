package org.example.phonebook.mapper;

import org.example.phonebook.dto.contact.ContactDto;
import org.example.phonebook.dto.contact.ContactResponseDto;
import org.example.phonebook.dto.contact.SaveContactRequest;
import org.example.phonebook.entity.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
/**
 * Mapper interface for converting between {@link Contact} entities and various Contact DTOs.
 *
 * <p>Uses MapStruct to generate implementations for mapping methods, supporting
 * conversions from request DTOs to entities, entities to response DTOs, and updating existing entities.
 *
 * <p>All mappings use Spring's component model for easy integration and injection.
 */
@Mapper(componentModel = "spring")
public interface ContactMapper {

    @Mapping(target = "id", ignore = true)
    Contact toEntity(SaveContactRequest saveContactRequest);

    ContactDto toDto(Contact contact);

    ContactDto toSaveContactDto(Contact contact);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateContactFromDto(ContactResponseDto dto, @MappingTarget Contact contact);


    List<ContactResponseDto> toContactResponserDtoList(List<Contact> all);

    ContactResponseDto toContactResponseDto(Contact updatedContact);
}
