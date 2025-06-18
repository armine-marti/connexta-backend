package org.example.phonebook.dto.contact;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.phonebook.entity.User;
import org.example.phonebook.enums.ContactStatus;
import org.example.phonebook.enums.RelationType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/**
 * Request DTO for creating or updating a contact.
 *
 * <p>Includes contact details along with the associated user and status.
 * Validation annotations ensure correct format and required fields.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveContactRequest {

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
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Enumerated(EnumType.STRING)
    private ContactStatus contactStatus;

}
