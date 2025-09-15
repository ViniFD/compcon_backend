package br.com.compcon.compcon_backend.controller;

import br.com.compcon.compcon_backend.controller.dto.SubjectDTO;
import br.com.compcon.compcon_backend.model.question.Subject;
import br.com.compcon.compcon_backend.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Subject> createSubject(@RequestBody @Valid SubjectDTO dto, UriComponentsBuilder uriBuilder) {
        Subject newSubject = subjectService.createSubject(dto);
        URI uri = uriBuilder.path("/api/subjects/{id}").buildAndExpand(newSubject.getId()).toUri();
        return ResponseEntity.created(uri).body(newSubject);
    }

    @GetMapping
    public ResponseEntity<List<Subject>> getAllSubjects() {
        List<Subject> subjects = subjectService.findAllSubjects();
        return ResponseEntity.ok(subjects);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable Long id, @RequestBody @Valid SubjectDTO dto) {
        Subject updatedSubject = subjectService.updateSubject(id, dto);
        return ResponseEntity.ok(updatedSubject);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }
}
