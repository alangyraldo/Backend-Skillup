package com.SkillUp.Backend.controller;

import com.SkillUp.Backend.dto.FavoritoRequest;
import com.SkillUp.Backend.dto.FavoritoResponse;
import com.SkillUp.Backend.service.FavoritoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {

    private final FavoritoService favoritoService;

    public FavoritoController(FavoritoService favoritoService) {
        this.favoritoService = favoritoService;
    }

    // GET: consultar favoritos de un usuario
    @GetMapping("/{idUsuario}")
    public ResponseEntity<List<FavoritoResponse>> listarFavoritos(
            @PathVariable Long idUsuario) {

        return ResponseEntity.ok(
                favoritoService.listarFavoritos(idUsuario)
        );
    }

    // POST: agregar un favorito
    @PostMapping
    public ResponseEntity<FavoritoResponse> agregarFavorito(
            @Valid @RequestBody FavoritoRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(favoritoService.agregarFavorito(request));
    }

    // DELETE: eliminar un favorito
    @DeleteMapping("/{idUsuario}/{idRecurso}")
    public ResponseEntity<Void> eliminarFavorito(
            @PathVariable Long idUsuario,
            @PathVariable Long idRecurso) {

        favoritoService.eliminarFavorito(idUsuario, idRecurso);

        return ResponseEntity.noContent().build();
    }
}
