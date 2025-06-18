package org.example.phonebook.dto.contact;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * Paginated response DTO for a list of contacts.
 *
 * <p>Encapsulates a subset of contact records along with the total number of elements.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactPageResponse {

    private List<ContactResponseDto> contacts;
    private long totalElements;
}
