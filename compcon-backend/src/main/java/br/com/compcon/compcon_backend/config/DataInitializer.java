package br.com.compcon.compcon_backend.config;

import br.com.compcon.compcon_backend.model.user.Role;
import br.com.compcon.compcon_backend.model.user.User;
import br.com.compcon.compcon_backend.model.user.UserRole;
import br.com.compcon.compcon_backend.repository.RoleRepository;
import br.com.compcon.compcon_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Componente que é executado na inicialização da aplicação para popular
 * a base de dados com dados essenciais, como as funções (Roles) e o utilizador administrador.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Este método é executado automaticamente pelo Spring Boot após a aplicação arrancar.
     */
    @Override
    public void run(String... args) throws Exception {
        Role adminRole = createRoleIfNotFound(UserRole.ADMIN);
        Role userRole = createRoleIfNotFound(UserRole.USER);

        createAdminUserIfNotFound(adminRole, userRole);
    }

    private Role createRoleIfNotFound(UserRole roleName) {
        return roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(null, roleName)));
    }

    private void createAdminUserIfNotFound(Role adminRole, Role userRole) {
        if (userRepository.findByEmail("viniciusfernandesdomingos@gmail.com").isEmpty()) {
            User admin = new User();
            admin.setUsername("ViniciusFDev");
            admin.setEmail("viniciusfernandesdomingos@gmail.com");
            admin.setPassword(passwordEncoder.encode("CopiniGodDemais123"));
            admin.setRoles(Set.of(adminRole, userRole));

            userRepository.save(admin);
            System.out.println(">>> Utilizador administrador criado com sucesso!");
        }
    }
}