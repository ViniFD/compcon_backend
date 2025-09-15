package br.com.compcon.compcon_backend.controller.dto;

import br.com.compcon.compcon_backend.model.question.Question;


public record QuestionSummaryDTO(
        Long id,
        String statement,
        String subjectName,
        String sourceName
) {

    public QuestionSummaryDTO(Question question) {
        this(
                question.getId(),
                // Lógica para truncar o enunciado se for muito longo, para a visualização em lista.
                question.getStatement().length() > 150 ? question.getStatement().substring(0, 150) + "..." : question.getStatement(),
                question.getSubject().getName(),
                question.getSource().getName()
        );
    }
}