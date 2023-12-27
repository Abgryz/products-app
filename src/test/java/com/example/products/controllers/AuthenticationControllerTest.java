package com.example.products.controllers;

import com.example.products.model.JwtToken;
import com.example.products.security.jwt.JwtUtils;
import com.example.products.tables.user.UserDto;
import com.example.products.tables.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private MockMvc mockMvc;

    private final UserDto userDto = UserDto.builder()
            .password("test")
            .username("test")
            .build();

    @AfterEach
    public void afterEach(){
        userRepository.deleteAll();
    }

    @Test
    public void testCreateUser() throws Exception {
        var query = post("/user/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDto));

        mockMvc.perform(query)
                .andExpect(status().isOk());
    }

    @Test
    public void testAuthenticate() throws Exception {
        var register = post("/user/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDto));
        mockMvc.perform(register);

        var query = post("/user/authenticate")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDto));
        var response = mockMvc.perform(query)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JwtToken jwtToken = objectMapper.readValue(response, JwtToken.class);
        System.out.println(jwtToken);
        assertEquals(jwtUtils.getUsername(jwtToken.getToken()), userDto.getUsername());
    }
}