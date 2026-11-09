package br.com.aula.library.controller;

import br.com.aula.library.dto.LeitorDTO;
import br.com.aula.library.service.LeitorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leitores")
public class LeitorController {

    private final LeitorService service;

    public LeitorController(LeitorService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LeitorDTO criar(@Valid @RequestBody LeitorDTO dto) {
        return service.criar(dto);
    }

    @GetMapping
    public List<LeitorDTO> listar() {
        return service.listar();
    }
}
