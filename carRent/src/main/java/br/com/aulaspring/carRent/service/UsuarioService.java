package br.com.aulaspring.carRent.service;

import br.com.aulaspring.carRent.dto.UsuarioDTO;
import br.com.aulaspring.carRent.model.Usuario;
import br.com.aulaspring.carRent.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository repository; //UsuarioRepository = extends

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public UsuarioDTO criar(UsuarioDTO dto) {
        Usuario u = new Usuario();
        u.setNome(dto.nome());
        u.setEmail(dto.email());
        return toDTO(repository.save(u));
    }

    public List<UsuarioDTO> listar() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    private UsuarioDTO toDTO(Usuario u) {
        return new UsuarioDTO(u.getId(), u.getNome(), u.getEmail());
    }
}
