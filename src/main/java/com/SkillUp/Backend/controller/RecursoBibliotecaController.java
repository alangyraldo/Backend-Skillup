package com.SkillUp.Backend.controller;

import com.SkillUp.Backend.dto.RecursoRequest;
import com.SkillUp.Backend.dto.RecursoResponse;
import com.SkillUp.Backend.service.RecursoBibliotecaService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recursos")
public class RecursoBibliotecaController {

    private final RecursoBibliotecaService recursoService;

    public RecursoBibliotecaController(
            RecursoBibliotecaService recursoService) {
        this.recursoService = recursoService;
    }

    // GET: listar recursos o filtrar por categoría
    @GetMapping
    public ResponseEntity<List<RecursoResponse>> listarRecursos(
            @RequestParam(required = false) Long categoriaId) {

        if (categoriaId != null) {
            return ResponseEntity.ok(
                    recursoService.listarPorCategoria(categoriaId)
            );
        }

        return ResponseEntity.ok(
                recursoService.listarRecursos()
        );
    }

    // GET: consultar recurso por ID
    @GetMapping("/{id}")
    public ResponseEntity<RecursoResponse> obtenerRecurso(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                recursoService.obtenerRecurso(id)
        );
    }

    // POST: crear recurso
    @PostMapping
    public ResponseEntity<RecursoResponse> crearRecurso(
            @Valid @RequestBody RecursoRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(recursoService.crearRecurso(request));
    }

    // PUT: actualizar recurso
    @PutMapping("/{id}")
    public ResponseEntity<RecursoResponse> actualizarRecurso(
            @PathVariable Long id,
            @Valid @RequestBody RecursoRequest request) {

        return ResponseEntity.ok(
                recursoService.actualizarRecurso(id, request)
        );
    }

    // DELETE: eliminar recurso
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRecurso(
            @PathVariable Long id) {

        recursoService.eliminarRecurso(id);

        return ResponseEntity.noContent().build();
    }
}
