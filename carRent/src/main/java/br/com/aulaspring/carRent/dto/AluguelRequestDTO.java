package br.com.aulaspring.carRent.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AluguelDTO(

        Long id,
        int usuario,
        int carro,
        LocalDateTime dataInicio,
        LocalDateTime dataFim ){}
