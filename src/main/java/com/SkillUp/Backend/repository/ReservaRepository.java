package com.SkillUp.Backend.repository;

import com.SkillUp.Backend.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByIdUsuario(Long idUsuario);
    boolean existsByIdUsuarioAndIdTutoriaAndEstado(Long idUsuario, Long idTutoria, String estado);
}