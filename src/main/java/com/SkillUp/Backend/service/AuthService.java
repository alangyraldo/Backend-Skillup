package com.SkillUp.Backend.service;

import com.SkillUp.Backend.dto.LoginRequest;
import com.SkillUp.Backend.dto.RegistroRequest;
import com.SkillUp.Backend.dto.UsuarioResponse;
import com.SkillUp.Backend.model.Usuario;
import com.SkillUp.Backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioResponse registrar(RegistroRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.nombre());
        usuario.setApellido(request.apellido());
        usuario.setEmail(request.email());
        usuario.setTipoUsuario(request.tipoUsuario());
        usuario.setTelefono(request.telefono());
        usuario.setContrasena(passwordEncoder.encode(request.contrasena()));

        return UsuarioResponse.desde(usuarioRepository.save(usuario));
    }

    public UsuarioResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas"));

        if (!passwordEncoder.matches(request.contrasena(), usuario.getContrasena())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas");
        }

        return UsuarioResponse.desde(usuario);
    }
}