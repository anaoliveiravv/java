package br.com.aulaspring.carRent.dto;

import jakarta.validation.constraints.NotBlank;

public record CarroDTO( //O DTO é a parte usada para transportar os dados entre o frontend e o backend, sem precisar mandar a Entity inteira.
        Long id,
        @NotBlank String marca,
        @NotBlank String modelo,
        int ano,
        @NotBlank String placa,
        boolean disponivel ) {}
//AQUI E TANTO O DTO REQUEST QUANTO O RESPONSE
//o SPRING faz uma validação das regras (annotations) que eu fiz no DTO na entrada do controller