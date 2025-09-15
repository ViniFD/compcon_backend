package br.com.compcon.compcon_backend.service;

import br.com.compcon.compcon_backend.controller.dto.SourceDTO;
import br.com.compcon.compcon_backend.model.question.Source;
import br.com.compcon.compcon_backend.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SourceService {

    @Autowired
    private SourceRepository sourceRepository;

    @Transactional
    public Source createSource(SourceDTO dto) {
        Source newSource = new Source(null, dto.name(), dto.institution());
        return sourceRepository.save(newSource);
    }

    @Transactional(readOnly = true)
    public List<Source> findAllSources() {
        return sourceRepository.findAll();
    }

    @Transactional
    public Source updateSource(Long id, SourceDTO dto) {
        Source source = sourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fonte não encontrada com o ID: " + id));
        source.setName(dto.name());
        source.setInstitution(dto.institution());
        return sourceRepository.save(source);
    }

    @Transactional
    public void deleteSource(Long id) {
        if (!sourceRepository.existsById(id)) {
            throw new RuntimeException("Fonte não encontrada com o ID: " + id);
        }
        sourceRepository.deleteById(id);
    }
}
