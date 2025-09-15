package br.com.compcon.compcon_backend.controller;

import br.com.compcon.compcon_backend.controller.dto.LoginRequestDTO;
import br.com.compcon.compcon_backend.controller.dto.LoginResponseDTO;
import br.com.compcon.compcon_backend.controller.dto.UserRegisterDTO;
import br.com.compcon.compcon_backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid UserRegisterDTO data) {
        authService.register(data);
        return ResponseEntity.status(201).build();
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) throws Exception {
        LoginResponseDTO response = authService.login(data);
        return ResponseEntity.ok(response);
    }
}