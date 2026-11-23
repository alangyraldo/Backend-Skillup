package com.SkillUp.Backend.repository;

import com.SkillUp.Backend.model.Tutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TutorRepository extends JpaRepository<Tutor, Long> {
    boolean existsByMateriaId(Long materiaId);
    boolean existsByUsuarioId(Long usuarioId);

    @Query("""
        SELECT t FROM Tutor t
        WHERE (:texto IS NULL
               OR LOWER(t.usuario.nombre) LIKE LOWER(CONCAT('%', :texto, '%'))
               OR LOWER(t.usuario.apellido) LIKE LOWER(CONCAT('%', :texto, '%'))
               OR LOWER(t.materia.nombre) LIKE LOWER(CONCAT('%', :texto, '%')))
          AND (:materiaId IS NULL OR t.materia.id = :materiaId)
          AND (:nivel IS NULL OR t.nivel = :nivel)
          AND (:precioMax IS NULL OR t.precio <= :precioMax)
          AND (:modalidad IS NULL OR t.modalidad = :modalidad)
        """)
    Page<Tutor> buscar(@Param("texto") String texto,
                       @Param("materiaId") Long materiaId,
                       @Param("nivel") String nivel,
                       @Param("precioMax") Integer precioMax,
                       @Param("modalidad") String modalidad,
                       Pageable pageable);
}