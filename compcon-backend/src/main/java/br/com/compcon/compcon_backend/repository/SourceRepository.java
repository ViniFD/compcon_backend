package br.com.compcon.compcon_backend.repository;

import br.com.compcon.compcon_backend.model.question.Source;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SourceRepository extends JpaRepository<Source, Long> {
}
