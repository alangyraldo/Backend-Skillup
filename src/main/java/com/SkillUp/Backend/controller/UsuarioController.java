package com.SkillUp.Backend.controller;

import com.SkillUp.Backend.dto.ActualizarUsuarioRequest;
import com.SkillUp.Backend.dto.UsuarioResponse;
import com.SkillUp.Backend.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/*
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository repo;

    public UsuarioController(UsuarioRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<UsuarioResponse> listar() {
        return repo.findAll().stream().map(UsuarioResponse::desde).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtener(@PathVariable Long id) {
        return repo.findById(id)
                .map(u -> ResponseEntity.ok(UsuarioResponse.desde(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizar(@PathVariable Long id,
                                                      @Valid @RequestBody ActualizarUsuarioRequest req) {
        return repo.findById(id).map(u -> {
            u.setNombre(req.nombre());
            u.setApellido(req.apellido());
            u.setTelefono(req.telefono());
            if (req.foto() != null) {
                u.setFoto(req.foto().isBlank() ? null : req.foto());
            }
            return ResponseEntity.ok(UsuarioResponse.desde(repo.save(u)));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
 */

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<UsuarioResponse> listar() {
        return repo.findAll().stream().map(UsuarioResponse::desde).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtener(@PathVariable Long id) {
        return repo.findById(id)
                .map(u -> ResponseEntity.ok(UsuarioResponse.desde(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizar(@PathVariable Long id,
                                                      @Valid @RequestBody ActualizarUsuarioRequest req) {
        return repo.findById(id).map(u -> {
            u.setNombre(req.nombre());
            u.setApellido(req.apellido());
            u.setTelefono(req.telefono());
            if (req.foto() != null) {
                u.setFoto(req.foto().isBlank() ? null : req.foto());
            }

            if (req.email() != null && !req.email().isBlank()) {
                String nuevoEmail = req.email().trim().toLowerCase();
                if (!nuevoEmail.equalsIgnoreCase(u.getEmail()) && repo.existsByEmail(nuevoEmail)) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Ese correo ya está en uso.");
                }
                u.setEmail(nuevoEmail);
            }

            if (req.contrasenaNueva() != null && !req.contrasenaNueva().isBlank()) {
                if (req.contrasenaActual() == null || !passwordEncoder.matches(req.contrasenaActual(), u.getContrasena())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña actual es incorrecta.");
                }
                if (req.contrasenaNueva().length() < 6) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La nueva contraseña debe tener mínimo 6 caracteres.");
                }
                u.setContrasena(passwordEncoder.encode(req.contrasenaNueva()));
            }

            return ResponseEntity.ok(UsuarioResponse.desde(repo.save(u)));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}