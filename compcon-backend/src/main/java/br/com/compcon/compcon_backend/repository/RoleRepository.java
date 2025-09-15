package br.com.compcon.compcon_backend.repository;

import br.com.compcon.compcon_backend.model.user.Role;
import br.com.compcon.compcon_backend.model.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Procura uma função pelo seu nome (que é uma Enum).
     * @param name O nome da função (ex: UserRole.USER).
     * @return Um Optional contendo a Role se for encontrada.
     */
    Optional<Role> findByName(UserRole name);
}
