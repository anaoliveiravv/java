package br.com.aulaspring.carRent.dto;

import java.time.LocalDateTime;

public record AluguelRequestDTO( //dados que vao ENTRAR

        int usuario,
        int carro ){}
