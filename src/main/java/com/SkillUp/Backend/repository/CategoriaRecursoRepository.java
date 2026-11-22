package com.SkillUp.Backend.repository;

import com.SkillUp.Backend.model.CategoriaRecurso;
import org.springframework.data.jpa.repository.JpaRepository;

// extendemos JpaRepository para tener todos su metodos, nuestro repository hereda las funciones de JpaRepository, CategoriaRecurso en la entidad con la que trabajar el repositorio y Long el tipo de dato de la clave primaria
public interface CategoriaRecursoRepository
        extends JpaRepository<CategoriaRecurso, Long> {
}
