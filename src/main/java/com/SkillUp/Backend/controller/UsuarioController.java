package com.SkillUp.Backend.controller;

import com.SkillUp.Backend.dto.ActualizarUsuarioRequest;
import com.SkillUp.Backend.dto.UsuarioResponse;
import com.SkillUp.Backend.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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