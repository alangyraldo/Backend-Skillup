package com.SkillUp.Backend.controller;

import com.SkillUp.Backend.dto.CategoriaRecursoRequest;
import com.SkillUp.Backend.dto.CategoriaRecursoResponse;
import com.SkillUp.Backend.service.CategoriaRecursoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaRecursoController {

    private final CategoriaRecursoService categoriaService;

    public CategoriaRecursoController(
            CategoriaRecursoService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaRecursoResponse>> listarCategorias() {

        return ResponseEntity.ok(
                categoriaService.listarCategorias()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaRecursoResponse> obtenerCategoria(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                categoriaService.obtenerCategoria(id)
        );
    }

    @PostMapping
    public ResponseEntity<CategoriaRecursoResponse> crearCategoria(
            @Valid @RequestBody CategoriaRecursoRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriaService.crearCategoria(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaRecursoResponse> actualizarCategoria(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRecursoRequest request) {

        return ResponseEntity.ok(
                categoriaService.actualizarCategoria(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(
            @PathVariable Long id) {

        categoriaService.eliminarCategoria(id);

        return ResponseEntity.noContent().build();
    }
}
