package com.example.products.services;

import com.example.products.mappers.UserMapper;
import com.example.products.tables.user.UserDto;
import com.example.products.tables.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public Optional<UserDto> getUser(String username){
        return userRepository.findByUsername(username)
                .map(userMapper::toDto);
    }

    @Transactional
    public void create(UserDto userDto){
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(userMapper.toEntity(userDto));
    }
}
