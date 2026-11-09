package br.com.aulaspring.carRent.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor //cria um construtor vazio
@AllArgsConstructor //cria um construtor com todos os atributos
@Entity

public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //o id será gerado automaticamente em serie pelo proprio banco
    private Long id;

    @Column(nullable = false) //column serve para personalizar as regras da coluna
    private String marca;

    @Column(nullable = false) //nullable = "aceita valor nulo"
    private String modelo;

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false, unique = true)
    private String placa;

    @Column
    private boolean disponivel = true;

}
