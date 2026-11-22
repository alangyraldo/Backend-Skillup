package com.SkillUp.Backend.service;

import com.SkillUp.Backend.dto.CategoriaRecursoRequest;
import com.SkillUp.Backend.dto.CategoriaRecursoResponse;
import com.SkillUp.Backend.model.CategoriaRecurso;
import com.SkillUp.Backend.repository.CategoriaRecursoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoriaRecursoService {

    private final CategoriaRecursoRepository categoriaRepository;

    public CategoriaRecursoService(
            CategoriaRecursoRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // Listar categorías
    @Transactional(readOnly = true)
    public List<CategoriaRecursoResponse> listarCategorias() {

        return categoriaRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    // Consultar categoría
    @Transactional(readOnly = true)
    public CategoriaRecursoResponse obtenerCategoria(Long id) {

        CategoriaRecurso categoria = buscarCategoria(id);

        return convertirAResponse(categoria);
    }

    // Crear categoría
    @Transactional
    public CategoriaRecursoResponse crearCategoria(
            CategoriaRecursoRequest request) {

        CategoriaRecurso categoria = new CategoriaRecurso();

        categoria.setNombre(request.getNombre());

        CategoriaRecurso guardada = categoriaRepository.save(categoria);

        return convertirAResponse(guardada);
    }

    // Actualizar categoría
    @Transactional
    public CategoriaRecursoResponse actualizarCategoria(
            Long id,
            CategoriaRecursoRequest request) {

        CategoriaRecurso categoria = buscarCategoria(id);

        categoria.setNombre(request.getNombre());

        CategoriaRecurso actualizada = categoriaRepository.save(categoria);

        return convertirAResponse(actualizada);
    }

    // Eliminar categoría
    @Transactional
    public void eliminarCategoria(Long id) {

        CategoriaRecurso categoria = buscarCategoria(id);

        categoriaRepository.delete(categoria);
    }

    // Buscar categoría por ID
    private CategoriaRecurso buscarCategoria(Long id) {

        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categoría no encontrada con ID: " + id
                ));
    }

    // Convertir entidad a DTO
    private CategoriaRecursoResponse convertirAResponse(
            CategoriaRecurso categoria) {

        return new CategoriaRecursoResponse(
                categoria.getId(),
                categoria.getNombre()
        );
    }
}
