package com.laurarojas.tablerointeractivoapi.service;

import com.laurarojas.tablerointeractivoapi.dto.RegisterUserDTO;
import com.laurarojas.tablerointeractivoapi.dto.ResponseMessageDTO;
import com.laurarojas.tablerointeractivoapi.dto.UserDTO;
import com.laurarojas.tablerointeractivoapi.entity.RoleEntity;
import com.laurarojas.tablerointeractivoapi.entity.UserEntity;
import com.laurarojas.tablerointeractivoapi.enums.Status;
import com.laurarojas.tablerointeractivoapi.exception.ApiException;
import com.laurarojas.tablerointeractivoapi.repository.RoleRepository;
import com.laurarojas.tablerointeractivoapi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseMessageDTO registerUser(RegisterUserDTO registerUserDTO) {
        if (userRepository.findByEmail(registerUserDTO.getEmail()).isPresent()) {
            throw new ApiException(
                    String.format("El email %s ya está registrado", registerUserDTO.getEmail(), registerUserDTO.getEmail()),
                    HttpStatus.CONFLICT.value(),
                    HttpStatus.CONFLICT.getReasonPhrase());
        };

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(registerUserDTO.getFirstName());
        userEntity.setLastName(registerUserDTO.getLastName());
        userEntity.setEmail(registerUserDTO.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        userEntity.setPhone(registerUserDTO.getPhone());
        userEntity.getRoles().add(getRoleByName("member"));
        userEntity.setStatus(Status.ACTIVE);
        userRepository.save(userEntity);
        return new ResponseMessageDTO("Usuario creado satisfactoriamente", HttpStatus.CREATED.value());
    }

    public RoleEntity getRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new ApiException(String.format("Role not found: %s", name),
                        HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase()));
    }

}
