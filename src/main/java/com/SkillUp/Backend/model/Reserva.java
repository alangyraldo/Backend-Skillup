package com.SkillUp.Backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "id_tutoria", nullable = false)
    private Long idTutoria;

    @Column(name = "fecha_reserva", insertable = false, updatable = false)
    private LocalDateTime fechaReserva;

    @Column(nullable = false)
    private String estado = "CONFIRMADA";

    public Reserva() {}

    public Reserva(Long idUsuario, Long idTutoria) {
        this.idUsuario = idUsuario;
        this.idTutoria = idTutoria;
        this.estado = "CONFIRMADA";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public Long getIdTutoria() { return idTutoria; }
    public void setIdTutoria(Long idTutoria) { this.idTutoria = idTutoria; }

    public LocalDateTime getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(LocalDateTime fechaReserva) { this.fechaReserva = fechaReserva; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}