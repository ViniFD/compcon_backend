package br.com.compcon.compcon_backend.controller;

import br.com.compcon.compcon_backend.controller.dto.QuestionCreateDTO;
import br.com.compcon.compcon_backend.controller.dto.QuestionDetailDTO;
import br.com.compcon.compcon_backend.controller.dto.QuestionSummaryDTO;
import br.com.compcon.compcon_backend.controller.dto.QuestionUpdateDTO;
import br.com.compcon.compcon_backend.model.question.Question;
import br.com.compcon.compcon_backend.model.user.User;
import br.com.compcon.compcon_backend.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<QuestionDetailDTO> createQuestion(@RequestBody @Valid QuestionCreateDTO dto, Authentication authentication, UriComponentsBuilder uriBuilder) {
        User author = (User) authentication.getPrincipal();
        Question newQuestion = questionService.createQuestion(dto, author.getEmail());
        URI uri = uriBuilder.path("/api/questions/{id}").buildAndExpand(newQuestion.getId()).toUri();
        return ResponseEntity.created(uri).body(new QuestionDetailDTO(newQuestion));
    }

    @GetMapping
    public ResponseEntity<Page<QuestionSummaryDTO>> getAllQuestions(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<QuestionSummaryDTO> questions = questionService.findAllQuestions(pageable);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDetailDTO> getQuestionById(@PathVariable Long id) {
        QuestionDetailDTO questionDTO = questionService.findQuestionById(id);
        return ResponseEntity.ok(questionDTO);
    }

    /**
     * Endpoint para atualizar uma questão existente.
     * @param id O ID da questão a ser atualizada, vindo da URL.
     * @param dto Os novos dados para a questão.
     * @param authentication O objeto de autenticação do utilizador logado.
     * @return Uma resposta HTTP 200 OK com os detalhes da questão atualizada.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<QuestionDetailDTO> updateQuestion(@PathVariable Long id, @RequestBody @Valid QuestionUpdateDTO dto, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        Question updatedQuestion = questionService.updateQuestion(id, dto, currentUser);
        return ResponseEntity.ok(new QuestionDetailDTO(updatedQuestion));
    }

    /**
     * Endpoint para apagar uma questão.
     * @param id O ID da questão a ser apagada, vindo da URL.
     * @param authentication O objeto de autenticação do utilizador logado.
     * @return Uma resposta HTTP 204 No Content, indicando sucesso sem corpo de resposta.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        questionService.deleteQuestion(id, currentUser);
        return ResponseEntity.noContent().build();
    }
}