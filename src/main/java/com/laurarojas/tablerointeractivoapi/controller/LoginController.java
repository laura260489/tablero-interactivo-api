package com.laurarojas.tablerointeractivoapi.controller;

import com.laurarojas.tablerointeractivoapi.dto.RequestLoginDTO;
import com.laurarojas.tablerointeractivoapi.dto.ResponseLoginDTO;
import com.laurarojas.tablerointeractivoapi.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RequestLoginDTO request) {
        ResponseLoginDTO responseTokenDTO = loginService.verifyLogin(request);
        return ResponseEntity.ok(responseTokenDTO);
    }
}
