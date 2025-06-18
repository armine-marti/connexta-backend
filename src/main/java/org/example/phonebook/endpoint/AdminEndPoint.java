package org.example.phonebook.endpoint;

import lombok.RequiredArgsConstructor;
import org.example.phonebook.dto.user.UserPageResponse;
import org.example.phonebook.dto.user.UserResponseDto;
import org.example.phonebook.security.CurrentUser;
import org.example.phonebook.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for admin-related user management operations.
 *
 * <p>Provides endpoints to retrieve, update, and delete users.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminEndPoint {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserPageResponse> getAll(
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "1000") int pageSize,
            @RequestParam(value = "search", required = false) String search,
            @AuthenticationPrincipal CurrentUser currentUser
    ) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);

        Page<UserResponseDto> page = userService.findAll(pageRequest, search, currentUser.getUsername());
        UserPageResponse response = new UserPageResponse(page.getContent(), page.getTotalElements());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable int id) {
        UserResponseDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody UserResponseDto userDto) {
        UserResponseDto userResponseDto = userService.updateUser(id, userDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
