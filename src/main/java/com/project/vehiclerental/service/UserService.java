package com.project.vehiclerental.service;

import com.project.vehiclerental.dto.UserDto;
import com.project.vehiclerental.entity.User;
import com.project.vehiclerental.advice.exception.UserNotFoundException;
import com.project.vehiclerental.mapper.UserMapper;
import com.project.vehiclerental.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserDto> getAllUser() {
        return userRepository.findAll()
                .stream()
                .parallel()
                .map(userMapper::toDto)
                .toList();
    }


    public UserDto getUser(Long id) {
        return userMapper.toDto(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)));
    }

    public UserDto saveUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toDto(userRepository.save(user));
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        User updatedUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        BeanUtils.copyProperties(userDto, updatedUser, "id");

        return userMapper.toDto(userRepository.save(updatedUser));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


}