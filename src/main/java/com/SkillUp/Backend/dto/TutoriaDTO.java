package com.SkillUp.Backend.dto;

import com.SkillUp.Backend.model.Tutoria;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class TutoriaDTO {

    private Long id;
    private Long idPerfilTutor;
    private Long idMateria;
    private String titulo;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private Integer duracionMinutos;
    private Integer cupos;
    private BigDecimal precio;
    private String modalidad;
    private String foto;

    public TutoriaDTO() {
    }

    public TutoriaDTO(Long id, Long idPerfilTutor, Long idMateria, String titulo,
                      LocalDate fecha, LocalTime horaInicio, Integer duracionMinutos,
                      Integer cupos, BigDecimal precio, String modalidad, String foto) {
        this.id = id;
        this.idPerfilTutor = idPerfilTutor;
        this.idMateria = idMateria;
        this.titulo = titulo;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.duracionMinutos = duracionMinutos;
        this.cupos = cupos;
        this.precio = precio;
        this.modalidad = modalidad;
        this.foto = foto;
    }

    public static TutoriaDTO fromEntity(Tutoria t) {
        return new TutoriaDTO(
                t.getId(),
                t.getIdPerfilTutor(),
                t.getIdMateria(),
                t.getTitulo(),
                t.getFecha(),
                t.getHoraInicio(),
                t.getDuracionMinutos(),
                t.getCupos(),
                t.getPrecio(),
                t.getModalidad(),
                t.getFoto()
        );
    }

    public Tutoria toEntity() {
        Tutoria tutoria = new Tutoria(idPerfilTutor, idMateria, titulo, fecha, horaInicio,
                duracionMinutos, cupos, precio, modalidad);
        tutoria.setFoto(foto);
        return tutoria;
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
    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }
}