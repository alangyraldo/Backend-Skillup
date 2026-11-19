package com.SkillUp.Backend.service;

import com.SkillUp.Backend.model.Tutoria;
import java.util.List;

public interface TutoriaService {

    List<Tutoria> findAll(Long usuarioId);
    Tutoria findById(Long id);
    Tutoria save(Tutoria tutoria);
    Tutoria update(Long id, Tutoria tutoria);
    Tutoria cambiarEstado(Long id, String nuevoEstado);
    void deleteById(Long id);
}