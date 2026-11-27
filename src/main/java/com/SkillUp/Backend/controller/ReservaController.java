package com.SkillUp.Backend.controller;

import com.SkillUp.Backend.dto.ReservaDetalleDTO;
import com.SkillUp.Backend.model.Reserva;
import com.SkillUp.Backend.service.ReservaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<ReservaDetalleDTO> listarPorUsuario(@PathVariable Long idUsuario) {
        return reservaService.listarPorUsuario(idUsuario);
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Map<String, Long> payload) {
        try {
            Long idUsuario = payload.get("idUsuario");
            Long idTutoria = payload.get("idTutoria");
            Reserva creada = reservaService.crearReserva(idUsuario, idTutoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        }
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelar(@PathVariable Long id) {
        try {
            reservaService.cancelarReserva(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        }
    }
}