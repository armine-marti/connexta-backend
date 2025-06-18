package org.example.phonebook.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.phonebook.enums.UserStatus;
import org.example.phonebook.enums.UserType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/**
 * Data transfer object representing user profile information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotEmpty(message = "Name can't be empty")
    private String name;
    private String surname;
    @Email(message = "Invalid email format")
    @NotEmpty(message = "Email can't be empty")
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String address;
    @NotNull
    private UserType userType;
    private UserStatus userStatus;

}
