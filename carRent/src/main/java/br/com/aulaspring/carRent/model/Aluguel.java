package br.com.aulaspring.carRent.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Aluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Usuario usuario; //usuario_id

    @ManyToOne(optional = false) //muitos objetos podem apontar para um mesmo objeto de outra classe;
    // essa entidade obrigatoriamente precisa estar relacionada à outra entidade
    private Carro carro; //carro_id

    @Column
    private LocalDateTime dataInicio;

    @Column
    private LocalDateTime dataFim;

}
