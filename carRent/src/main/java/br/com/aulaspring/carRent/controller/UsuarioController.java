package br.com.aulaspring.carRent.controller;

import br.com.aulaspring.carRent.dto.UsuarioDTO;
import br.com.aulaspring.carRent.model.Usuario;
import br.com.aulaspring.carRent.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping //criar
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO criar(@Valid @RequestBody UsuarioDTO dto) {
        return service.criar(dto);
    }

    @GetMapping //listar
    public List<UsuarioDTO> listar() {
        return service.listar();
    }
}

