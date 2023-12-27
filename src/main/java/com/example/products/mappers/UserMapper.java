package com.example.products.mappers;

import com.example.products.tables.product.Product;
import com.example.products.tables.product.ProductRepository;
import com.example.products.tables.user.User;
import com.example.products.tables.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<UserDto, User>{
    @Override
    public UserDto toDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    @Override
    public User toEntity(UserDto userDTO) {
        return User.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .build();
    }
}
