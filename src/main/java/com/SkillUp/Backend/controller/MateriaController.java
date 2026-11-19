package com.SkillUp.Backend.controller;

import com.SkillUp.Backend.model.Materia;
import com.SkillUp.Backend.service.MateriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materias")
public class MateriaController {

    private final MateriaService materiaService;

    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @GetMapping
    public List<Materia> listar() {
        return materiaService.listar();
    }

    @GetMapping("/{id}")
    public Materia obtener(@PathVariable Long id) {
        return materiaService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Materia crear(@Valid @RequestBody Materia materia) {
        return materiaService.crear(materia);
    }

    @PutMapping("/{id}")
    public Materia actualizar(@PathVariable Long id, @Valid @RequestBody Materia materia) {
        return materiaService.actualizar(id, materia);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        materiaService.eliminar(id);
    }
}