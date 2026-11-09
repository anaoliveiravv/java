package br.com.aula.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Leitor leitor; //id_leitor

    @ManyToOne
    private Livro livro; //id_livro

    private LocalDateTime dataEmprestimo;
    private LocalDateTime dataDevolucao;
}
