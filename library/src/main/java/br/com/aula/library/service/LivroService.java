package br.com.aula.library.service;

import br.com.aula.library.dto.LivroDTO;
import br.com.aula.library.model.Livro;
import br.com.aula.library.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {
    private final LivroRepository repository;

    public LivroService(LivroRepository repository) {
        this.repository = repository;
    }

    public LivroDTO criar(LivroDTO dto) {
        Livro livro = new Livro();
        livro.setId(dto.id());
        livro.setTitulo(dto.titulo());
        livro.setAutor(dto.autor());
        livro.setDisponivel(dto.disponivel());
        return toDTO(repository.save(livro));
    }

    public List<LivroDTO> listar() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    private LivroDTO toDTO(Livro livro) {
        return new LivroDTO (
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.isDisponivel()
        );
    }
}
