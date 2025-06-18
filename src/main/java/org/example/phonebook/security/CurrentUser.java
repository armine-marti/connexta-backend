package org.example.phonebook.security;

import org.example.phonebook.entity.User;
import org.springframework.security.core.authority.AuthorityUtils;
/**
 * Custom {@link org.springframework.security.core.userdetails.User} implementation
 * that wraps the application's {@link User} entity.
 *
 * <p>This class extends Spring Security's User details to include
 * the domain-specific {@link User} object, allowing access to additional user data
 * during authentication and authorization processes.
 */
public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private final User user;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPassword(),
                AuthorityUtils.createAuthorityList(user.getUserType().name()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}