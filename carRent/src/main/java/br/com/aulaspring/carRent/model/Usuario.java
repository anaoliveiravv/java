package br.com.aulaspring.carRent.model; //nosso dominio

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor //ou @Data ao inves do getter e setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincremento + identity = serial
    private Long id;

    @Column(nullable = false) //esse atributo nao pode ser nulo, nullable = notnull
    private String nome;

    @Column(nullable = false, unique = true)
    private String email; //string = varchar
    

}
