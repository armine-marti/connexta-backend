package org.example.phonebook.dto.contact;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.phonebook.enums.RelationType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/**
 * Data transfer object representing a user's contact.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {

    private String name;
    private String surname;
    private String email;
    private String phone;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String address;
    @Enumerated(EnumType.STRING)
    private RelationType relationType;

}
