package com.SkillUp.Backend.service;

import com.SkillUp.Backend.model.Tutoria;
import com.SkillUp.Backend.repository.TutoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.List;

@Service
@Primary
public class TutoriaServiceJPA implements TutoriaService {

    private final TutoriaRepository tutoriaRepository;

    @Autowired
    public TutoriaServiceJPA(TutoriaRepository tutoriaRepository) {
        this.tutoriaRepository = tutoriaRepository;
    }

    @Override
    public List<Tutoria> findAll(Long usuarioId) {
        return usuarioId == null
                ? tutoriaRepository.findAll()
                : tutoriaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public Tutoria findById(Long id) {
        return tutoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Tutoría no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public Tutoria save(Tutoria tutoria) {
        validarTutoria(tutoria, 0L);
        return tutoriaRepository.save(tutoria);
    }

    @Override
    @Transactional
    public Tutoria update(Long id, Tutoria actualizada) {
        Tutoria existente = findById(id);
        validarTutoria(actualizada, id);
        existente.setIdPerfilTutor(actualizada.getIdPerfilTutor());
        existente.setIdMateria(actualizada.getIdMateria());
        existente.setTitulo(actualizada.getTitulo());
        existente.setFecha(actualizada.getFecha());
        existente.setHoraInicio(actualizada.getHoraInicio());
        existente.setDuracionMinutos(actualizada.getDuracionMinutos());
        existente.setCupos(actualizada.getCupos());
        existente.setPrecio(actualizada.getPrecio());
        existente.setModalidad(actualizada.getModalidad());
        return tutoriaRepository.save(existente);
    }

    @Override
    public void deleteById(Long id) {
        if (!tutoriaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Tutoría no encontrada con ID: " + id);
        }
        tutoriaRepository.deleteById(id);
    }

    private void validarTutoria(Tutoria t, Long excluirId) {
        if (t.getIdPerfilTutor() == null || t.getIdMateria() == null
                || t.getTitulo() == null || t.getTitulo().isBlank()
                || t.getFecha() == null || t.getHoraInicio() == null
                || t.getDuracionMinutos() == null || t.getCupos() == null
                || t.getPrecio() == null || t.getModalidad() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Faltan campos obligatorios de la tutoría");
        }
        if (t.getDuracionMinutos() < 1 || t.getCupos() < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La duración y los cupos deben ser mayores a 0");
        }
        LocalTime fin = t.getHoraInicio().plusMinutes(t.getDuracionMinutos());
        if (!fin.isAfter(t.getHoraInicio())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La tutoría debe terminar el mismo día (antes de medianoche)");
        }
        long choques = tutoriaRepository.contarChoques(
                t.getIdPerfilTutor(), t.getFecha(), t.getHoraInicio(), fin, excluirId);
        if (choques > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "El tutor ya tiene una tutoría en ese horario");
        }
    }
}