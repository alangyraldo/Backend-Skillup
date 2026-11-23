package com.SkillUp.Backend.dto;

import com.SkillUp.Backend.model.TipoUsuario;
import com.SkillUp.Backend.model.Usuario;

public record UsuarioResponse(
        Long id,
        String nombre,
        String apellido,
        String email,
        TipoUsuario tipoUsuario,
        String telefono,
        String token
) {
    public static UsuarioResponse desde(Usuario u) {
        return desde(u, null);
    }

    public static UsuarioResponse desde(Usuario u, String token) {
        return new UsuarioResponse(
                u.getId(),
                u.getNombre(),
                u.getApellido(),
                u.getEmail(),
                u.getTipoUsuario(),
                u.getTelefono(),
                token
        );
    }
}