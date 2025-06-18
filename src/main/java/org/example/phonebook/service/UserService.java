package org.example.phonebook.service;

import org.example.phonebook.dto.user.SaveUserRequest;
import org.example.phonebook.dto.user.UserDto;
import org.example.phonebook.dto.user.UserResponseDto;
import org.example.phonebook.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
/**
 * Service interface for managing {@link User} entities.
 *
 * <p>Provides methods for user CRUD operations, search, and pagination support.
 */
public interface UserService {

    Page<UserResponseDto> findAll(Pageable pageable, String search, String currentEmail);

    UserDto save(SaveUserRequest saveUserRequest);

    Optional<User> findByEmail(String email);

    UserResponseDto findById(int id);

    UserResponseDto updateUser(int id, UserResponseDto userDto);

    void deleteById(int id);
}
