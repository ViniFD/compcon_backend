package br.com.compcon.compcon_backend.controller;

import br.com.compcon.compcon_backend.controller.dto.UserDetailDTO;
import br.com.compcon.compcon_backend.model.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
public class UserController {


    @GetMapping("/me")
    public ResponseEntity<UserDetailDTO> getCurrentUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(new UserDetailDTO(user));
    }
}