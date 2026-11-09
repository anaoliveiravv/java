package br.com.aula.library.service;

import br.com.aula.library.dto.LeitorDTO;
import br.com.aula.library.model.Leitor;
import br.com.aula.library.repository.LeitorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeitorService {
    private final LeitorRepository repository;

    public LeitorService(LeitorRepository repository) {
        this.repository = repository;
    }

    public LeitorDTO criar(LeitorDTO dto) {
        Leitor leitor = new Leitor();
        leitor.setId(dto.id());
        leitor.setNome(dto.nome());
        leitor.setEmail(dto.email());
        return toDTO(repository.save(leitor));
    }

    public List<LeitorDTO> listar() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    private LeitorDTO toDTO(Leitor leitor) {
        return new LeitorDTO (
        leitor.getId(),
        leitor.getNome(),
        leitor.getEmail());
    }
}
