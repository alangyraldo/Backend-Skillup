package com.SkillUp.Backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/*
public record ActualizarUsuarioRequest(
        @NotBlank String nombre,
        @NotBlank String apellido,
        String telefono
) {}

 */

public record ActualizarUsuarioRequest(
        @NotBlank(message = "El nombre es obligatorio") String nombre,
        @NotBlank(message = "El apellido es obligatorio") String apellido,
        String telefono,
        @Email(message = "Correo inválido") String email,
        String contrasenaActual,
        String contrasenaNueva
) {}