package org.example.phonebook.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.phonebook.enums.UserStatus;
import org.example.phonebook.enums.UserType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Request DTO for creating or updating a user.
 *
 * <p>Includes user credentials, personal details, and status information.
 * Validation annotations enforce required fields and format constraints.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveUserRequest {

    @NotEmpty(message = "Name can't be empty")
    private String name;
    private String surname;
    @Email(message = "Invalid email format")
    @NotEmpty(message = "Email can't be empty")
    private String email;
    @NotEmpty(message = "Password can't be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String address;
    @NotNull
    private UserType userType;
    private UserStatus userStatus;

}
