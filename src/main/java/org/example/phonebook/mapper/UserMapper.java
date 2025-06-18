package org.example.phonebook.mapper;

import org.example.phonebook.dto.user.SaveUserRequest;
import org.example.phonebook.dto.user.UserDto;
import org.example.phonebook.dto.user.UserResponseDto;
import org.example.phonebook.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper interface for converting between {@link User} entities and various User DTOs.
 *
 * <p>Uses MapStruct for generating implementation with Spring component model and ignores unmapped targets.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User toEntity(SaveUserRequest saveUserRequest);

    SaveUserRequest toSaveUserRequest(User user);

    UserDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateUserFromDto(UserResponseDto dto, @MappingTarget User user);

    List<UserDto> toDtoList(List<User> users);

    List<UserResponseDto> toUserResponserDtoList(List<User> all);

    UserResponseDto toUserResponseDto(User updatedUser);
}