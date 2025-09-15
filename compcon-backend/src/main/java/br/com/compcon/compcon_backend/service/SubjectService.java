package br.com.compcon.compcon_backend.service;

import br.com.compcon.compcon_backend.controller.dto.SubjectDTO;
import br.com.compcon.compcon_backend.model.question.Subject;
import br.com.compcon.compcon_backend.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Transactional
    public Subject createSubject(SubjectDTO dto) {
        // TODO: Adicionar lógica para verificar se já existe uma matéria com o mesmo nome.
        Subject newSubject = new Subject(null, dto.name());
        return subjectRepository.save(newSubject);
    }

    @Transactional(readOnly = true)
    public List<Subject> findAllSubjects() {
        return subjectRepository.findAll();
    }

    @Transactional
    public Subject updateSubject(Long id, SubjectDTO dto) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matéria não encontrada com o ID: " + id));
        subject.setName(dto.name());
        return subjectRepository.save(subject);
    }

    @Transactional
    public void deleteSubject(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new RuntimeException("Matéria não encontrada com o ID: " + id);
        }
        subjectRepository.deleteById(id);
    }
}
