package br.com.aula.library.dto;

import br.com.aula.library.model.Leitor;
import br.com.aula.library.model.Livro;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public record EmprestimoDTO (
        Long id,
        @ManyToOne Leitor leitor,
        @ManyToOne Livro livro,
        LocalDateTime dataEmprestimo,
        LocalDateTime dataDevolucao

){
}
