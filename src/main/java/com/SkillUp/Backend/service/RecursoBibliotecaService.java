package com.SkillUp.Backend.service;

import com.SkillUp.Backend.dto.RecursoRequest;
import com.SkillUp.Backend.dto.RecursoResponse;

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
    public List<RecursoResponse> listarRecursos() {

        return recursoRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    // Consultar recurso por ID
    @Transactional(readOnly = true)
    public RecursoResponse obtenerRecurso(Long id) {

        RecursoBiblioteca recurso = buscarRecurso(id);

        return convertirAResponse(recurso);
    }

    // Consultar recursos por categoría
    @Transactional(readOnly = true)
    public List<RecursoResponse> listarPorCategoria(Long idCategoria) {

        if (!categoriaRepository.existsById(idCategoria)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Categoría no encontrada con ID: " + idCategoria
            );
        }

        return recursoRepository.findByCategoria_Id(idCategoria)
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    // Crear recurso
    @Transactional
    public RecursoResponse crearRecurso(RecursoRequest request) {

        validarRecurso(request);

        CategoriaRecurso categoria = buscarCategoria(
                request.getCategoriaId()
        );

        RecursoBiblioteca recurso = new RecursoBiblioteca();

        recurso.setCategoria(categoria);
        recurso.setTitulo(request.getTitulo());
        recurso.setTipoRecurso(request.getTipoRecurso());
        recurso.setUrlArchivo(request.getUrlArchivo());
        recurso.setDescripcion(request.getDescripcion());

        RecursoBiblioteca guardado = recursoRepository.save(recurso);

        return convertirAResponse(guardado);
    }

    // Actualizar recurso
    @Transactional
    public RecursoResponse actualizarRecurso(
            Long id,
            RecursoRequest request) {

        validarRecurso(request);

        RecursoBiblioteca recurso = buscarRecurso(id);

        CategoriaRecurso categoria = buscarCategoria(
                request.getCategoriaId()
        );

        recurso.setCategoria(categoria);
        recurso.setTitulo(request.getTitulo());
        recurso.setTipoRecurso(request.getTipoRecurso());
        recurso.setUrlArchivo(request.getUrlArchivo());
        recurso.setDescripcion(request.getDescripcion());

        RecursoBiblioteca actualizado = recursoRepository.save(recurso);

        return convertirAResponse(actualizado);
    }

    // Eliminar recurso
    @Transactional
    public void eliminarRecurso(Long id) {

        RecursoBiblioteca recurso = buscarRecurso(id);

        recursoRepository.delete(recurso);
    }

    // Buscar recurso
    private RecursoBiblioteca buscarRecurso(Long id) {

        return recursoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Recurso no encontrado con ID: " + id
                ));
    }

    // Buscar categoría
    private CategoriaRecurso buscarCategoria(Long idCategoria) {

        return categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categoría no encontrada con ID: " + idCategoria
                ));
    }

    // Validar datos recibidos
    private void validarRecurso(RecursoRequest request) {

        if (request == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Los datos del recurso son obligatorios"
            );
        }
    }

    // Convertir entidad a DTO
    private RecursoResponse convertirAResponse(
            RecursoBiblioteca recurso) {

        return new RecursoResponse(
                recurso.getId(),
                recurso.getCategoria().getId(),
                recurso.getCategoria().getNombre(),
                recurso.getTitulo(),
                recurso.getTipoRecurso(),
                recurso.getUrlArchivo(),
                recurso.getDescripcion()
        );
    }
}
