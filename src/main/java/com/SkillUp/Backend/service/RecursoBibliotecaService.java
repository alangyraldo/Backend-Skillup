package com.SkillUp.Backend.service;

import com.SkillUp.Backend.model.CategoriaRecurso;
import com.SkillUp.Backend.model.RecursoBiblioteca;
import com.SkillUp.Backend.repository.CategoriaRecursoRepository;
import com.SkillUp.Backend.repository.RecursoBibliotecaRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RecursoBibliotecaService {

    private final RecursoBibliotecaRepository recursoRepository;
    private final CategoriaRecursoRepository categoriaRepository;

    public RecursoBibliotecaService(
            RecursoBibliotecaRepository recursoRepository,
            CategoriaRecursoRepository categoriaRepository) {

        this.recursoRepository = recursoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // Listar todos los recursos
    @Transactional(readOnly = true)
    public List<RecursoBiblioteca> listarRecursos() {
        return recursoRepository.findAll();
    }

    // Consultar un recurso por ID
    @Transactional(readOnly = true)
    public RecursoBiblioteca obtenerRecurso(Long id) {

        return recursoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Recurso no encontrado con ID: " + id
                ));
    }

    // Consultar recursos por categoría
    @Transactional(readOnly = true)
    public List<RecursoBiblioteca> listarPorCategoria(Long idCategoria) {

        if (!categoriaRepository.existsById(idCategoria)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Categoría no encontrada con ID: " + idCategoria
            );
        }

        return recursoRepository.findByCategoria_Id(idCategoria);
    }

    // Crear un recurso
    @Transactional
    public RecursoBiblioteca crearRecurso(RecursoBiblioteca recurso) {

        validarRecurso(recurso);

        CategoriaRecurso categoria = obtenerCategoria(
                recurso.getCategoria().getId()
        );

        recurso.setCategoria(categoria);

        return recursoRepository.save(recurso);
    }

    // Actualizar un recurso
    @Transactional
    public RecursoBiblioteca actualizarRecurso(
            Long id,
            RecursoBiblioteca datosRecurso) {

        validarRecurso(datosRecurso);

        RecursoBiblioteca recurso = obtenerRecurso(id);

        CategoriaRecurso categoria = obtenerCategoria(
                datosRecurso.getCategoria().getId()
        );

        recurso.setCategoria(categoria);
        recurso.setTitulo(datosRecurso.getTitulo());
        recurso.setTipoRecurso(datosRecurso.getTipoRecurso());
        recurso.setUrlArchivo(datosRecurso.getUrlArchivo());
        recurso.setDescripcion(datosRecurso.getDescripcion());

        return recursoRepository.save(recurso);
    }

    // Eliminar un recurso
    @Transactional
    public void eliminarRecurso(Long id) {

        RecursoBiblioteca recurso = obtenerRecurso(id);

        recursoRepository.delete(recurso);
    }

    // Buscar una categoría existente
    private CategoriaRecurso obtenerCategoria(Long idCategoria) {

        return categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categoría no encontrada con ID: " + idCategoria
                ));
    }

    // Validar los datos del recurso
    private void validarRecurso(RecursoBiblioteca recurso) {

        if (recurso == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Los datos del recurso son obligatorios"
            );
        }

        if (recurso.getTitulo() == null ||
                recurso.getTitulo().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El título del recurso es obligatorio"
            );
        }

        if (recurso.getTipoRecurso() == null ||
                recurso.getTipoRecurso().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El tipo de recurso es obligatorio"
            );
        }

        if (recurso.getCategoria() == null ||
                recurso.getCategoria().getId() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debe seleccionar una categoría"
            );
        }
    }
}
