package org.example.phonebook.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.phonebook.dto.UserAuthRequest;
import org.example.phonebook.dto.user.SaveUserRequest;
import org.example.phonebook.enums.UserType;
import org.example.phonebook.service.UserService;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserAuthEndPointTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void login() throws Exception {

        String email = "loggeduser_" + System.currentTimeMillis() + "@example.com";
        String rawPassword = "test123";


        SaveUserRequest user = SaveUserRequest.builder()
                .name("Test")
                .surname("User")
                .email(email)
                .password(passwordEncoder.encode(rawPassword))
                .userType(UserType.USER)
                .build();

        userService.save(user);
        UserAuthRequest loginRequest = UserAuthRequest.builder()
                .email(email)
                .password(rawPassword)
                .build();

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.surname").value("User"));
    }


    @Test
    void register() throws Exception {

        SaveUserRequest request = SaveUserRequest.builder()
                .name("Test")
                .surname("User")
                .email("testUser_" + System.currentTimeMillis() + "@usermail.com")
                .password("test123")
                .userType(UserType.USER)
                .build();

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}