package br.com.aula.library.controller;

import br.com.aula.library.dto.LivroDTO;
import br.com.aula.library.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService service;

    public LivroController(LivroService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LivroDTO criar(@Valid @RequestBody LivroDTO dto) {
        return service.criar(dto);
    }

    @GetMapping
    public List<LivroDTO> listar() {
        return service.listar();
    }

}
