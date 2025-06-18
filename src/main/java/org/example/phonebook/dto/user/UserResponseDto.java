package org.example.phonebook.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.phonebook.enums.UserStatus;
import org.example.phonebook.enums.UserType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/**
 * Data transfer object representing user profile information.
 *
 * <p>Returned by API endpoints after retrieving user details.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private int id;
    private String name;
    private String surname;
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String address;
    private UserType userType;
    private UserStatus userStatus;
}
