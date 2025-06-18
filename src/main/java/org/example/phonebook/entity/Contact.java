package org.example.phonebook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.phonebook.enums.ContactStatus;
import org.example.phonebook.enums.RelationType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Entity representing a contact belonging to a user.
 *
 * <p>Stores personal details and relationship information,
 * linked to a {@link User} via a many-to-one association.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String email;
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
    @Column(name = "status_contact")
    private ContactStatus contactStatus;
}
