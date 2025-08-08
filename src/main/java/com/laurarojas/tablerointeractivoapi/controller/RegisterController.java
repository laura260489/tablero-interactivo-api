package com.laurarojas.tablerointeractivoapi.controller;

import com.laurarojas.tablerointeractivoapi.dto.RegisterUserDTO;
import com.laurarojas.tablerointeractivoapi.dto.ResponseMessageDTO;
import com.laurarojas.tablerointeractivoapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserDTO registerUserDTO) {
        ResponseMessageDTO response = userService.registerUser(registerUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(response);
    }
}

