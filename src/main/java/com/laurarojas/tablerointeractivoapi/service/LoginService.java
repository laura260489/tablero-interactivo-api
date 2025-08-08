package com.laurarojas.tablerointeractivoapi.service;

import com.laurarojas.tablerointeractivoapi.dto.RequestLoginDTO;
import com.laurarojas.tablerointeractivoapi.dto.ResponseLoginDTO;
import com.laurarojas.tablerointeractivoapi.entity.RoleEntity;
import com.laurarojas.tablerointeractivoapi.entity.UserEntity;
import com.laurarojas.tablerointeractivoapi.exception.ApiException;
import com.laurarojas.tablerointeractivoapi.repository.UserRepository;
import com.laurarojas.tablerointeractivoapi.util.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoginService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public LoginService(PasswordEncoder passwordEncoder,
                       UserRepository userRepository,
                       JwtProvider  jwtProvider) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    public ResponseLoginDTO verifyLogin(RequestLoginDTO loginDTO) throws ApiException {
        Optional<UserEntity> user = userRepository.findUserWithRolesByEmail(loginDTO.getEmail());

        if(!user.isPresent()) {
            throw new ApiException("Usuario no registrado", HttpStatus.UNAUTHORIZED.value(),
                    HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }

        if(!passwordEncoder.matches(loginDTO.getPassword(), user.get().getPassword())) {
            throw new ApiException("Correo y/o contraseña incorrecta", HttpStatus.UNAUTHORIZED.value(),
                    HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }
        UserEntity userEntity = user.get();
        List<String> roleNames = userEntity.getRoles().stream()
                .map(RoleEntity::getName).collect(Collectors.toList());
        String token = jwtProvider.generateToken(userEntity.getId(), userEntity.getEmail(),
                userEntity.getFirstName(),userEntity.getLastName(), roleNames);
        return new ResponseLoginDTO(HttpStatus.OK.value(), "Inicio de sesión exitoso", token);
    }
}
