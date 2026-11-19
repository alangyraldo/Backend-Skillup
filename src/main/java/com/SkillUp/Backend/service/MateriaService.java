package com.SkillUp.Backend.service;

import com.SkillUp.Backend.model.Materia;
import com.SkillUp.Backend.repository.MateriaRepository;
import com.SkillUp.Backend.repository.TutorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MateriaService {

    private final MateriaRepository materiaRepository;
    private final TutorRepository tutorRepository;

    public MateriaService(MateriaRepository materiaRepository, TutorRepository tutorRepository) {
        this.materiaRepository = materiaRepository;
        this.tutorRepository = tutorRepository;
    }

    public List<Materia> listar() {
        return materiaRepository.findAll();
    }

    public Materia buscarPorId(Long id) {
        return materiaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Materia no encontrada"));
    }

    public Materia crear(Materia materia) {
        if (materiaRepository.existsByNombreIgnoreCase(materia.getNombre())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una materia con ese nombre");
        }
        materia.setId(null); // para que siempre sea una materia nueva
        return materiaRepository.save(materia);
    }

    public Materia actualizar(Long id, Materia datos) {
        Materia materia = buscarPorId(id);
        boolean cambioNombre = !materia.getNombre().equalsIgnoreCase(datos.getNombre());
        if (cambioNombre && materiaRepository.existsByNombreIgnoreCase(datos.getNombre())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una materia con ese nombre");
        }
        materia.setNombre(datos.getNombre());
        materia.setDescripcion(datos.getDescripcion());
        return materiaRepository.save(materia);
    }

    public void eliminar(Long id) {
        Materia materia = buscarPorId(id);
        if (tutorRepository.existsByMateriaId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar: hay tutores que usan esta materia");
        }
        materiaRepository.delete(materia);
    }
}