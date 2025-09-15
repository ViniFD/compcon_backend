package br.com.compcon.compcon_backend.repository;

import br.com.compcon.compcon_backend.model.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuestionRepository extends JpaRepository<Question, Long> {

}
