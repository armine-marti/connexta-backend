package org.example.phonebook.dto.contact;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.phonebook.enums.RelationType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/**
 * Response DTO representing detailed contact information.
 *
 * <p>Used to return contact data in API responses, typically after retrieval or update operations.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactResponseDto {

    private int id;
    @NotNull
    private String name;
    private String surname;
    @Email(message = "Invalid email format")
    @NotEmpty(message = "Email can't be empty")
    private String email;
    @Pattern(regexp = "^(\\+?[0-9]{10,15})$", message = "Phone number must be between 10 and 15 digits and may start with +")
    @NotEmpty(message = "Phone can't be empty")
    private String phone;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String address;
    @Enumerated(EnumType.STRING)
    private RelationType relationType;

}
