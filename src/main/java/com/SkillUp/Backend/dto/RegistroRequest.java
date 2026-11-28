package com.SkillUp.Backend.dto;

import com.SkillUp.Backend.model.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegistroRequest(
        @NotBlank @Size(max = 50) String nombre,
        @NotBlank @Size(max = 50) String apellido,
        @NotBlank @Email @Size(max = 100) String email,
        @NotBlank @Size(min = 6, max = 100) String contrasena,
        @NotNull TipoUsuario tipoUsuario,
        @Size(max = 20) String telefono,
        String foto
) {}