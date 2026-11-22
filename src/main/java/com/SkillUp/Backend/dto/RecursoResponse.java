package com.SkillUp.Backend.dto;

public record RecursoResponse(
        Long id,
        Long categoriaId,
        String nombreCategoria,
        String titulo,
        String tipoRecurso,
        String urlArchivo,
        String descripcion
) {
}
