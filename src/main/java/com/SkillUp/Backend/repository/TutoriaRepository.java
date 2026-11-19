package com.SkillUp.Backend.repository;

import com.SkillUp.Backend.model.Tutoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface TutoriaRepository extends JpaRepository<Tutoria, Long> {

    @Query(value = """
            SELECT t.* FROM tutorias t
            JOIN perfil_tutor p ON p.id = t.id_perfil_tutor
            WHERE p.id_usuario = :usuarioId
            """, nativeQuery = true)
    List<Tutoria> findByUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query(value = """
            SELECT COUNT(*) FROM tutorias t
            WHERE t.id_perfil_tutor = :tutor
              AND t.fecha = :fecha
              AND t.hora_inicio < :fin
              AND ADDTIME(t.hora_inicio, SEC_TO_TIME(t.duracion_minutos * 60)) > :inicio
              AND t.id <> :excluirId
            """, nativeQuery = true)
    long contarChoques(@Param("tutor") Long tutor,
                       @Param("fecha") LocalDate fecha,
                       @Param("inicio") LocalTime inicio,
                       @Param("fin") LocalTime fin,
                       @Param("excluirId") Long excluirId);
}