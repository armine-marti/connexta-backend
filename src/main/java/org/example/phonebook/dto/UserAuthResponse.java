package org.example.phonebook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.phonebook.enums.UserType;
/**
 * Authentication response DTO returned after successful login.
 *
 * <p>Contains the issued JWT token and basic user profile information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAuthResponse {

    private String token;
    private String name;
    private String surname;
    private int userId;
    private UserType userType;
}
