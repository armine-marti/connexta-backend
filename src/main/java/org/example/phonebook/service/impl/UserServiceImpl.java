package org.example.phonebook.service.impl;


import lombok.RequiredArgsConstructor;
import org.example.phonebook.dto.user.SaveUserRequest;
import org.example.phonebook.dto.user.UserDto;
import org.example.phonebook.dto.user.UserResponseDto;
import org.example.phonebook.entity.User;
import org.example.phonebook.enums.UserStatus;
import org.example.phonebook.exception.InvalidEmailException;
import org.example.phonebook.mapper.UserMapper;
import org.example.phonebook.repository.UserRepository;
import org.example.phonebook.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
/**
 * Implementation of the {@link UserService} interface.
 * <p>
 * Provides user management functionalities including creation, update, soft deletion,
 * search, and retrieval by ID or email. Integrates with the {@link UserRepository}
 * and uses {@link UserMapper} for DTO-entity conversions.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Page<UserResponseDto> findAll(Pageable pageable, String search, String currentEmail) {
        Page<User> users = (search == null || search.isBlank())
                ? userRepository.findAllExcludingEmail(currentEmail, pageable)
                : userRepository.searchUsersExcludingEmail(search, currentEmail, pageable);

        return users.map(userMapper::toUserResponseDto);
    }


    @Override
    public UserDto save(SaveUserRequest saveUserRequest) {
        saveUserRequest.setUserStatus(UserStatus.ACTIVE_USER);
        return userMapper.toDto(userRepository.save(userMapper.toEntity(saveUserRequest)));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserResponseDto updateUser(int id, UserResponseDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if (!existingUser.getEmail().equals(userDto.getEmail())) {
            Optional<User> userWithSameEmail = userRepository.findByEmail(userDto.getEmail());

            if (userWithSameEmail.isPresent() && userWithSameEmail.get().getId() != id) {
                throw new InvalidEmailException("Please try different email.");
            }
        }

        userMapper.updateUserFromDto(userDto, existingUser);
        User updatedUser = userRepository.save(existingUser);
        return userMapper.toUserResponseDto(updatedUser);

    }

    @Override
    public UserResponseDto findById(int id) {
        User user = userRepository
                .findById(id)
                .orElse(null);
        if (user == null) {
            return null;
        }
        return userMapper.toUserResponseDto(user);
    }

    @Override
    public void deleteById(int id) {
        if (!userRepository.existsById(id)) {
            //throw new UserNotFoundException("User not found with " + id + " id");
        }
        userRepository.softDeleteById(id);
    }
}