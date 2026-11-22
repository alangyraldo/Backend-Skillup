package com.SkillUp.Backend.dto;

import java.time.LocalDateTime;

public record FavoritoResponse(
        Long idUsuario,
        Long idRecurso,
        String tituloRecurso,
        LocalDateTime fechaAgregado
) {
}
