package br.com.aula.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LeitorDTO (
        Long id,
        @NotBlank String nome,
        @NotBlank @Email String email){}
