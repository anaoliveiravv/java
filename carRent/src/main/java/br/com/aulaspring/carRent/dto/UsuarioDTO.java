package br.com.aulaspring.carRent.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO( //record = so mostrar e capturar dados (por isso e utilizado no dto). Serve prinicpalmente para carregar e transportar dados
        Long id,
        @NotBlank String nome,
        @NotBlank @Email String email) {}

//dto = vai receebr os dados e definir o que o usuario vai ver/ requisitou, pra nao mostrar dados que nao e pra mostrar. (no frontend