package br.com.compcon.compcon_backend.controller;

import br.com.compcon.compcon_backend.controller.dto.SourceDTO;
import br.com.compcon.compcon_backend.model.question.Source;
import br.com.compcon.compcon_backend.service.SourceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/sources")
public class SourceController {

    @Autowired
    private SourceService sourceService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Source> createSource(@RequestBody @Valid SourceDTO dto, UriComponentsBuilder uriBuilder) {
        Source newSource = sourceService.createSource(dto);
        URI uri = uriBuilder.path("/api/sources/{id}").buildAndExpand(newSource.getId()).toUri();
        return ResponseEntity.created(uri).body(newSource);
    }

    @GetMapping
    public ResponseEntity<List<Source>> getAllSources() {
        List<Source> sources = sourceService.findAllSources();
        return ResponseEntity.ok(sources);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Source> updateSource(@PathVariable Long id, @RequestBody @Valid SourceDTO dto) {
        Source updatedSource = sourceService.updateSource(id, dto);
        return ResponseEntity.ok(updatedSource);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSource(@PathVariable Long id) {
        sourceService.deleteSource(id);
        return ResponseEntity.noContent().build();
    }
}
