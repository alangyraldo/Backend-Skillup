package com.SkillUp.Backend.controller;

import com.SkillUp.Backend.model.Tutor;
import com.SkillUp.Backend.service.TutorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tutores")
public class TutorController {

    private final TutorService tutorService;

    public TutorController(TutorService tutorService) {
        this.tutorService = tutorService;
    }

    // Ejemplo: /api/tutores?texto=foto&nivel=Principiante&precioMax=50000&page=0&size=3
    @GetMapping
    public Page<Tutor> listar(
            @RequestParam(required = false) String texto,
            @RequestParam(required = false) Long materiaId,
            @RequestParam(required = false) String nivel,
            @RequestParam(required = false) Integer precioMax,
            @RequestParam(required = false) String modalidad,
            @PageableDefault(size = 3, sort = "id") Pageable pageable) {
        return tutorService.listar(texto, materiaId, nivel, precioMax, modalidad, pageable);
    }

    @GetMapping("/{id}")
    public Tutor obtener(@PathVariable Long id) {
        return tutorService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tutor crear(@Valid @RequestBody Tutor tutor) {
        return tutorService.crear(tutor);
    }

    @PutMapping("/{id}")
    public Tutor actualizar(@PathVariable Long id, @Valid @RequestBody Tutor tutor) {
        return tutorService.actualizar(id, tutor);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        tutorService.eliminar(id);
    }
}
