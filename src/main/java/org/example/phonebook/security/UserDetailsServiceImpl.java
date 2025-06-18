package org.example.phonebook.security;

import lombok.RequiredArgsConstructor;
import org.example.phonebook.entity.User;
import org.example.phonebook.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
/**
 * Implementation of Spring Security's {@link UserDetailsService} interface.
 *
 * <p>Loads user-specific data during authentication by retrieving {@link User} entities
 * from the {@link UserService} based on the provided username (email).
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byEmail = userService.findByEmail(username);
        if (byEmail.isPresent()) {
            User userFromDb = byEmail.get();
            return new CurrentUser(userFromDb);
        }
        throw new UsernameNotFoundException("User with " + username + " does not exists");
    }
}