package com.SkillUp.Backend.dto;

import jakarta.validation.constraints.NotBlank;

public record ActualizarUsuarioRequest(
        @NotBlank String nombre,
        @NotBlank String apellido,
        String telefono
) {}