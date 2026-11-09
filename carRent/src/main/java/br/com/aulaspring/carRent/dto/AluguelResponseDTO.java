package br.com.aulaspring.carRent.dto;

import java.time.LocalDateTime;

public record AluguelResponseDTO( //dados que vao SAIR
    Long id,
    int usuario,
    int carro,
    LocalDateTime dataInicio,
    LocalDateTime dataFim

) {
}
