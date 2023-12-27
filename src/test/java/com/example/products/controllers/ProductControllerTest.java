package com.example.products.controllers;

import com.example.products.model.JwtToken;
import com.example.products.model.ProductsRequest;
import com.example.products.security.jwt.JwtUtils;
import com.example.products.tables.product.ProductDto;
import com.example.products.tables.product.ProductRepository;
import com.example.products.tables.user.User;
import com.example.products.tables.user.UserDto;
import com.example.products.tables.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MockMvc mockMvc;
    private final UserDto userDto = UserDto.builder()
            .password("test")
            .username("test")
            .build();
    private final String productsRequestJson = """
            {
            "table" : "products",
            "records" : [
            {
            "entryDate": "03-01-2023",
            "itemCode": "11111",
            "itemName": "Test Inventory 1",
            "itemQuantity": "20",
            "status": "Paid"
            },
            {
            "entryDate": "03-01-2023",
            "itemCode": "11111",
            "itemName": "Test Inventory 2",
            "itemQuantity": "20",
            "status": "Paid"
            }] }""";
    private final List<ProductDto> expectedProducts = List.of(
            ProductDto.builder()
                    .id(1L)
                    .entryDate(LocalDate.of(2023, 1, 3))
                    .itemQuantity(20)
                    .itemName("Test Inventory 1")
                    .itemCode(11111L)
                    .status("Paid")
                    .user("test")
                    .build(),
            ProductDto.builder()
                    .id(2L)
                    .entryDate(LocalDate.of(2023, 1, 3))
                    .itemQuantity(20)
                    .itemName("Test Inventory 2")
                    .itemCode(11111L)
                    .status("Paid")
                    .user("test")
                    .build()
    );

    @BeforeEach
    public void beforeEach() throws Exception {
        userRepository.deleteAll();
        productRepository.deleteAll();

        var query = post("/user/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDto));

        mockMvc.perform(query);
    }

    @Test
    public void testGetAllProducts() throws Exception {
        JwtToken jwtToken = getToken();

        var addProducts = post("/products/add")
                .contentType("application/json")
                .content(productsRequestJson)
                .header("Authorization", String.format("Bearer %s", jwtToken.getToken()));
        mockMvc.perform(addProducts);

        var query = get("/products/all")
                .header("Authorization", String.format("Bearer %s", jwtToken.getToken()));
        var response = mockMvc.perform(query)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<ProductDto> products = objectMapper.readValue(response, new TypeReference<>() {
        });
        assertEquals(products, expectedProducts);
    }

    @Test
    public void testAddProducts() throws Exception {
        JwtToken jwtToken = getToken();

        var query = post("/products/add")
                .contentType("application/json")
                .content(productsRequestJson)
                .header("Authorization", String.format("Bearer %s", jwtToken.getToken()));
        mockMvc.perform(query)
                .andExpect(status().isOk());
    }

    private JwtToken getToken() throws Exception {
        var auth = post("/user/authenticate")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDto));
        var response = mockMvc.perform(auth)
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println(response);
        return objectMapper.readValue(response, JwtToken.class);
    }
}