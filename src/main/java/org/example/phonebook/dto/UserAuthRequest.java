package org.example.phonebook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Authentication request DTO used for user login.
 *
 * <p>Contains user credentials submitted by the client
 * to initiate authentication.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAuthRequest {

    private String email;
    private String password;

}
