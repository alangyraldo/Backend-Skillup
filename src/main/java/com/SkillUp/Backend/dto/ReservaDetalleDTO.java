package com.SkillUp.Backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaDetalleDTO {
    private Long id;
    private Long idTutoria;
    private String titulo;
    private String tutor;
    private LocalDate fecha;
    private LocalTime hora;
    private BigDecimal precio;
    private String estado;

    public ReservaDetalleDTO(Long id, Long idTutoria, String titulo, String tutor,
                             LocalDate fecha, LocalTime hora, BigDecimal precio, String estado) {
        this.id = id;
        this.idTutoria = idTutoria;
        this.titulo = titulo;
        this.tutor = tutor;
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public Long getIdTutoria() { return idTutoria; }
    public String getTitulo() { return titulo; }
    public String getTutor() { return tutor; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHora() { return hora; }
    public BigDecimal getPrecio() { return precio; }
    public String getEstado() { return estado; }
}