package com.SkillUp.Backend.service;

import com.SkillUp.Backend.model.Materia;
import com.SkillUp.Backend.model.Tutor;
import com.SkillUp.Backend.model.Usuario;
import com.SkillUp.Backend.repository.MateriaRepository;
import com.SkillUp.Backend.repository.TutorRepository;
import com.SkillUp.Backend.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TutorService {

    private final TutorRepository tutorRepository;
    private final MateriaRepository materiaRepository;
    private final UsuarioRepository usuarioRepository;

    public TutorService(TutorRepository tutorRepository,
                        MateriaRepository materiaRepository,
                        UsuarioRepository usuarioRepository) {
        this.tutorRepository = tutorRepository;
        this.materiaRepository = materiaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Page<Tutor> listar(String texto, Long materiaId, String nivel,
                              Integer precioMax, String modalidad, Pageable pageable) {
        return tutorRepository.buscar(limpiar(texto), materiaId, limpiar(nivel),
                precioMax, limpiar(modalidad), pageable);
    }

    public Tutor buscarPorId(Long id) {
        return tutorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tutor no encontrado"));
    }

    public Tutor crear(Tutor tutor) {
        tutor.setId(null);
        Usuario usuario = buscarUsuario(tutor.getUsuario() != null ? tutor.getUsuario().getId() : null);
        if (tutorRepository.existsByUsuarioId(usuario.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este usuario ya tiene un perfil de tutor");
        }
        tutor.setUsuario(usuario);
        tutor.setMateria(buscarMateria(tutor.getMateria() != null ? tutor.getMateria().getId() : null));
        return tutorRepository.save(tutor);
    }

    public Tutor actualizar(Long id, Tutor datos) {
        Tutor tutor = buscarPorId(id);
        tutor.setNivel(datos.getNivel());
        tutor.setModalidad(datos.getModalidad());
        tutor.setPrecio(datos.getPrecio());
        tutor.setExperiencia(datos.getExperiencia());
        tutor.setFoto(datos.getFoto());
        tutor.setMateria(buscarMateria(datos.getMateria() != null ? datos.getMateria().getId() : null));
        return tutorRepository.save(tutor);
    }

    public void eliminar(Long id) {
        tutorRepository.delete(buscarPorId(id));
    }

    private Materia buscarMateria(Long materiaId) {
        if (materiaId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debes indicar el id de la materia");
        }
        return materiaRepository.findById(materiaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "La materia indicada no existe"));
    }

    private Usuario buscarUsuario(Integer usuarioId) {
        if (usuarioId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debes indicar el id del usuario");
        }
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario indicado no existe"));
    }

    private String limpiar(String valor) {
        return (valor == null || valor.isBlank()) ? null : valor.trim();
    }
}