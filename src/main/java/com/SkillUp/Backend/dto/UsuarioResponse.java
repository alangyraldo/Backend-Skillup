package com.SkillUp.Backend.dto;

import com.SkillUp.Backend.model.TipoUsuario;
import com.SkillUp.Backend.model.Usuario;

public record UsuarioResponse(
        Long id,
        String nombre,
        String apellido,
        String email,
        TipoUsuario tipoUsuario,
        String telefono
) {
    public static UsuarioResponse desde(Usuario u) {
        return new UsuarioResponse(
                u.getId(),
                u.getNombre(),
                u.getApellido(),
                u.getEmail(),
                u.getTipoUsuario(),
                u.getTelefono()
        );
    }
}