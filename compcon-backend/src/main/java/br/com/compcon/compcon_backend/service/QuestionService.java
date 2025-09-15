package br.com.compcon.compcon_backend.service;

import br.com.compcon.compcon_backend.controller.dto.QuestionCreateDTO;
import br.com.compcon.compcon_backend.controller.dto.QuestionDetailDTO;
import br.com.compcon.compcon_backend.controller.dto.QuestionSummaryDTO;
import br.com.compcon.compcon_backend.controller.dto.QuestionUpdateDTO;
import br.com.compcon.compcon_backend.model.question.Alternative;
import br.com.compcon.compcon_backend.model.question.Question;
import br.com.compcon.compcon_backend.model.question.Source;
import br.com.compcon.compcon_backend.model.question.Subject;
import br.com.compcon.compcon_backend.model.user.User;
import br.com.compcon.compcon_backend.model.user.UserRole;
import br.com.compcon.compcon_backend.repository.QuestionRepository;
import br.com.compcon.compcon_backend.repository.SourceRepository;
import br.com.compcon.compcon_backend.repository.SubjectRepository;
import br.com.compcon.compcon_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private SourceRepository sourceRepository;

    @Transactional
    public Question createQuestion(QuestionCreateDTO dto, String authorEmail) {
        User author = userRepository.findByEmail(authorEmail).orElseThrow(() -> new RuntimeException("Utilizador não encontrado."));
        Subject subject = subjectRepository.findById(dto.subjectId()).orElseThrow(() -> new RuntimeException("Matéria não encontrada."));
        Source source = sourceRepository.findById(dto.sourceId()).orElseThrow(() -> new RuntimeException("Fonte não encontrada."));

        Question question = new Question();
        question.setStatement(dto.statement());
        question.setAuthor(author);
        question.setSubject(subject);
        question.setSource(source);

        List<Alternative> alternatives = dto.alternatives().stream().map(altDto -> {
            Alternative alternative = new Alternative();
            alternative.setText(altDto.text());
            alternative.setCorrect(altDto.isCorrect());
            alternative.setQuestion(question);
            return alternative;
        }).collect(Collectors.toList());
        question.setAlternatives(alternatives);

        return questionRepository.save(question);
    }

    @Transactional(readOnly = true)
    public QuestionDetailDTO findQuestionById(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new RuntimeException("Questão não encontrada com o ID: " + id));
        return new QuestionDetailDTO(question);
    }

    @Transactional(readOnly = true)
    public Page<QuestionSummaryDTO> findAllQuestions(Pageable pageable) {
        Page<Question> questionsPage = questionRepository.findAll(pageable);
        return questionsPage.map(QuestionSummaryDTO::new);
    }

    @Transactional
    public Question updateQuestion(Long id, QuestionUpdateDTO dto, User currentUser) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Questão não encontrada com o ID: " + id));

        validateOwnership(currentUser, question);

        Subject subject = subjectRepository.findById(dto.subjectId()).orElseThrow(() -> new RuntimeException("Matéria não encontrada."));
        Source source = sourceRepository.findById(dto.sourceId()).orElseThrow(() -> new RuntimeException("Fonte não encontrada."));

        question.setStatement(dto.statement());
        question.setSubject(subject);
        question.setSource(source);

        question.getAlternatives().clear();
        dto.alternatives().forEach(altDto -> {
            Alternative alternative = new Alternative();
            alternative.setText(altDto.text());
            alternative.setCorrect(altDto.isCorrect());
            alternative.setQuestion(question);
            question.getAlternatives().add(alternative);
        });

        return questionRepository.save(question);
    }

    @Transactional
    public void deleteQuestion(Long id, User currentUser) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Questão não encontrada com o ID: " + id));

        validateOwnership(currentUser, question);

        questionRepository.delete(question);
    }

    private void validateOwnership(User currentUser, Question question) {
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals(UserRole.ADMIN));

        if (!isAdmin && !question.getAuthor().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("O utilizador não tem permissão para modificar este recurso.");
        }
    }
}
