package org.example.phonebook.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * Paginated response DTO for a list of users.
 *
 * <p>Encapsulates a subset of user records along with the total number of elements.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class  UserPageResponse {

    private List<UserResponseDto> users;
    private long totalElements;
}


