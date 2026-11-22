package com.SkillUp.Backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FavoritoRequest(

        @NotNull(message = "El ID del usuario es obligatorio")
        @Positive(message = "El ID del usuario debe ser positivo")
        Long idUsuario,

        @NotNull(message = "El ID del recurso es obligatorio")
        @Positive(message = "El ID del recurso debe ser positivo")
        Long idRecurso

) {
}
