package br.com.compcon.compcon_backend.repository;

import br.com.compcon.compcon_backend.model.question.Subject;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
