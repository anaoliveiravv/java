package br.com.aula.library.dto;

import jakarta.validation.constraints.NotBlank;

public record LivroDTO (
        Long id,
        @NotBlank String titulo,
        @NotBlank String autor,
        boolean disponivel ){}
