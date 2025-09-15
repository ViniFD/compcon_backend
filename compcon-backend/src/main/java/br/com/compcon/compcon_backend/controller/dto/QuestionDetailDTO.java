package br.com.compcon.compcon_backend.controller.dto;

import br.com.compcon.compcon_backend.model.question.Question;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


public record QuestionDetailDTO(
        Long id,
        String statement,
        String subjectName,
        String sourceName,
        String authorUsername,
        Instant createdAt,
        List<AlternativeResponseDTO> alternatives
) {


    public record AlternativeResponseDTO(Long id, String text) {}


    public QuestionDetailDTO(Question question) {
        this(
                question.getId(),
                question.getStatement(),
                question.getSubject().getName(),
                question.getSource().getName(),
                question.getAuthor().getUsername(),
                question.getCreatedAt(),
                question.getAlternatives().stream()
                        .map(alt -> new AlternativeResponseDTO(alt.getId(), alt.getText()))
                        .collect(Collectors.toList())
        );
    }
}
