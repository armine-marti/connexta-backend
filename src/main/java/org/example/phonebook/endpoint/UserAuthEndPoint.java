package org.example.phonebook.endpoint;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.example.phonebook.dto.UserAuthRequest;
import org.example.phonebook.dto.UserAuthResponse;
import org.example.phonebook.dto.user.SaveUserRequest;
import org.example.phonebook.entity.User;
import org.example.phonebook.exception.InvalidEmailException;
import org.example.phonebook.exception.InvalidPasswordException;
import org.example.phonebook.service.UserService;
import org.example.phonebook.util.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * REST controller responsible for user authentication and registration endpoints.
 *
 * <p>Handles login and registration operations, including credential validation,
 * password encoding, and JWT token generation.
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserAuthEndPoint {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil tokenUtil;


    @PostMapping("/login")
    public ResponseEntity<UserAuthResponse> login(@RequestBody UserAuthRequest userAuthRequest) {

        Optional<User> byEmail = userService.findByEmail(userAuthRequest.getEmail());

        if (byEmail.isEmpty()) {
            throw new InvalidEmailException("Invalid email, please try again");
        }

        User user = byEmail.get();
        if (!passwordEncoder.matches(userAuthRequest.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Incorrect password, please try again");
        }

        if (passwordEncoder.matches(userAuthRequest.getPassword(), user.getPassword())) {
            return ResponseEntity
                    .ok(UserAuthResponse.builder()
                            .token(tokenUtil.generateTokenWithRoles(user.getEmail(), user.getUserType()))
                            .name(user.getName())
                            .surname(user.getSurname())
                            .userId(user.getId())
                            .userType(user.getUserType())
                            .build());
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .build();
    }

    @PostMapping("/register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Email already exists"),
            @ApiResponse(responseCode = "200", description = "Registration is successful")
    })
    public ResponseEntity<?> register(@RequestBody SaveUserRequest saveUserRequest) {
        if (userService.findByEmail(saveUserRequest.getEmail()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .build();
        }
        saveUserRequest.setPassword(passwordEncoder.encode(saveUserRequest.getPassword()));
        userService.save(saveUserRequest);
        return ResponseEntity
                .ok()
                .build();
    }

}
