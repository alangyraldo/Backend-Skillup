package com.SkillUp.Backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tutorias")
public class Tutoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_perfil_tutor", nullable = false)
    private Long idPerfilTutor;

    @Column(name = "id_materia", nullable = false)
    private Long idMateria;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "duracion_minutos", nullable = false)
    private Integer duracionMinutos;

    @Column(nullable = false)
    private Integer cupos;

    @Column(nullable = false)
    private BigDecimal precio;

    @Column(nullable = false)
    private String modalidad;

    // ACTIVA, CANCELADA o COMPLETADA
    @Column(nullable = false)
    private String estado;

    public Tutoria() {
    }

    public Tutoria(Long idPerfilTutor, Long idMateria, String titulo, LocalDate fecha,
                   LocalTime horaInicio, Integer duracionMinutos, Integer cupos,
                   BigDecimal precio, String modalidad) {
        this.idPerfilTutor = idPerfilTutor;
        this.idMateria = idMateria;
        this.titulo = titulo;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.duracionMinutos = duracionMinutos;
        this.cupos = cupos;
        this.precio = precio;
        this.modalidad = modalidad;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdPerfilTutor() { return idPerfilTutor; }
    public void setIdPerfilTutor(Long idPerfilTutor) { this.idPerfilTutor = idPerfilTutor; }

    public Long getIdMateria() { return idMateria; }
    public void setIdMateria(Long idMateria) { this.idMateria = idMateria; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public Integer getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(Integer duracionMinutos) { this.duracionMinutos = duracionMinutos; }

    public Integer getCupos() { return cupos; }
    public void setCupos(Integer cupos) { this.cupos = cupos; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public String getModalidad() { return modalidad; }
    public void setModalidad(String modalidad) { this.modalidad = modalidad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}