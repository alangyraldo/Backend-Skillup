package com.SkillUp.Backend.service;

import com.SkillUp.Backend.dto.ReservaDetalleDTO;
import com.SkillUp.Backend.model.Tutor;
import com.SkillUp.Backend.model.Reserva;
import com.SkillUp.Backend.model.Tutoria;
import com.SkillUp.Backend.model.Usuario;
import com.SkillUp.Backend.repository.TutorRepository;
import com.SkillUp.Backend.repository.ReservaRepository;
import com.SkillUp.Backend.repository.TutoriaRepository;
import com.SkillUp.Backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final TutoriaRepository tutoriaRepository;
    private final TutorRepository perfilTutorRepository;
    private final UsuarioRepository usuarioRepository;

    public ReservaService(ReservaRepository reservaRepository,
                          TutoriaRepository tutoriaRepository,
                          TutorRepository perfilTutorRepository,
                          UsuarioRepository usuarioRepository) {
        this.reservaRepository = reservaRepository;
        this.tutoriaRepository = tutoriaRepository;
        this.perfilTutorRepository = perfilTutorRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<ReservaDetalleDTO> listarPorUsuario(Long idUsuario) {
        return reservaRepository.findByIdUsuario(idUsuario).stream()
                .filter(r -> !"CANCELADA".equals(r.getEstado()))
                .map(r -> {
                    Tutoria t = tutoriaRepository.findById(r.getIdTutoria()).orElse(null);
                    String nombreTutor = obtenerNombreTutor(t);

                    return new ReservaDetalleDTO(
                            r.getId(),
                            r.getIdTutoria(),
                            t != null ? t.getTitulo() : "Tutoría #" + r.getIdTutoria(),
                            nombreTutor,
                            t != null ? t.getFecha() : null,
                            t != null ? t.getHoraInicio() : null,
                            t != null ? t.getPrecio() : null,
                            r.getEstado()
                    );
                })
                .collect(Collectors.toList());
    }

    private String obtenerNombreTutor(Tutoria t) {
        if (t == null || t.getIdPerfilTutor() == null) return "Tutor";

        return perfilTutorRepository.findById(t.getIdPerfilTutor())
                .map(perfil -> perfil.getUsuario().getNombre() + " " + perfil.getUsuario().getApellido())
                .orElse("Tutor #" + t.getIdPerfilTutor());
    }

    @Transactional
    public Reserva crearReserva(Long idUsuario, Long idTutoria) {
        if (reservaRepository.existsByIdUsuarioAndIdTutoriaAndEstado(idUsuario, idTutoria, "CONFIRMADA")) {
            throw new IllegalArgumentException("Ya tienes una reserva confirmada para esta tutoría.");
        }

        Tutoria tutoria = tutoriaRepository.findById(idTutoria)
                .orElseThrow(() -> new IllegalArgumentException("La tutoría no existe."));

        if (tutoria.getCupos() == null || tutoria.getCupos() <= 0) {
            throw new IllegalArgumentException("No hay cupos disponibles para esta tutoría.");
        }

        tutoria.setCupos(tutoria.getCupos() - 1);
        tutoriaRepository.save(tutoria);

        return reservaRepository.save(new Reserva(idUsuario, idTutoria));
    }

    @Transactional
    public void cancelarReserva(Long idReserva) {
        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new IllegalArgumentException("La reserva no existe."));

        if (!"CANCELADA".equals(reserva.getEstado())) {
            reserva.setEstado("CANCELADA");
            reservaRepository.save(reserva);

            tutoriaRepository.findById(reserva.getIdTutoria()).ifPresent(tutoria -> {
                tutoria.setCupos(tutoria.getCupos() + 1);
                tutoriaRepository.save(tutoria);
            });
        }
    }
}