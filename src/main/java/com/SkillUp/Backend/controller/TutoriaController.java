package com.SkillUp.Backend.controller;

import com.SkillUp.Backend.dto.TutoriaDTO;
import com.SkillUp.Backend.service.TutoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tutorias")
public class TutoriaController {

    private final TutoriaService tutoriaService;

    @Autowired
    public TutoriaController(TutoriaService tutoriaService) {
        this.tutoriaService = tutoriaService;
    }

    @GetMapping
    public List<TutoriaDTO> getAllTutorias(@RequestParam(required = false) Long usuarioId) {
        return tutoriaService.findAll(usuarioId)
                .stream()
                .map(TutoriaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TutoriaDTO getTutoriaById(@PathVariable Long id) {
        return TutoriaDTO.fromEntity(tutoriaService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TutoriaDTO createTutoria(@RequestBody TutoriaDTO tutoriaDTO) {
        return TutoriaDTO.fromEntity(tutoriaService.save(tutoriaDTO.toEntity()));
    }

    @PutMapping("/{id}")
    public TutoriaDTO updateTutoria(@PathVariable Long id, @RequestBody TutoriaDTO tutoriaDTO) {
        return TutoriaDTO.fromEntity(tutoriaService.update(id, tutoriaDTO.toEntity()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTutoria(@PathVariable Long id) {
        tutoriaService.deleteById(id);
    }
}