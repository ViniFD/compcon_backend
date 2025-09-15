package br.com.compcon.compcon_backend.service;

import br.com.compcon.compcon_backend.controller.dto.LoginRequestDTO;
import br.com.compcon.compcon_backend.controller.dto.LoginResponseDTO;
import br.com.compcon.compcon_backend.controller.dto.UserRegisterDTO;
import br.com.compcon.compcon_backend.model.user.Role;
import br.com.compcon.compcon_backend.model.user.User;
import br.com.compcon.compcon_backend.model.user.UserRole;
import br.com.compcon.compcon_backend.repository.RoleRepository;
import br.com.compcon.compcon_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private TokenService tokenService;

    @Transactional
    public void register(UserRegisterDTO data) {
        if (userRepository.findByEmail(data.email()).isPresent()) {
            throw new IllegalStateException("Erro: O email já está a ser utilizado.");
        }

        Role userRole = roleRepository.findByName(UserRole.USER)
                .orElseThrow(() -> new IllegalStateException("Erro: A função 'USER' não foi encontrada."));

        User newUser = new User();
        newUser.setUsername(data.username());
        newUser.setEmail(data.email());
        newUser.setPassword(passwordEncoder.encode(data.password()));
        newUser.setRoles(Set.of(userRole));

        userRepository.save(newUser);
    }

    public LoginResponseDTO login(LoginRequestDTO data) throws Exception {
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        var authToken = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        Authentication authentication = authenticationManager.authenticate(authToken);
        User authenticatedUser = (User) authentication.getPrincipal();
        String token = tokenService.generateToken(authenticatedUser);
        return new LoginResponseDTO(token);
    }
}
