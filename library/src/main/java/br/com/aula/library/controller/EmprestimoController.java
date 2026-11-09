package br.com.aula.library.controller;

import br.com.aula.library.dto.EmprestimoDTO;
import br.com.aula.library.service.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService service;

    public EmprestimoController(EmprestimoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmprestimoDTO criar(@Valid @RequestBody EmprestimoDTO dto) {
        return service.criar(dto);
    }

    public List<EmprestimoDTO> listar() {
        return service.listar();
    }
}
