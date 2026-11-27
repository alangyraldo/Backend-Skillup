package com.SkillUp.Backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "perfil_tutor")
public class  Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El usuario es obligatorio")
    @OneToOne
    @JoinColumn(name = "id_usuario", unique = true, nullable = false)
    private Usuario usuario;

    @NotNull(message = "La materia es obligatoria")
    @ManyToOne
    @JoinColumn(name = "id_materia", nullable = false)
    private Materia materia;

    private String nivel;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    @Column(name = "precio_base", nullable = false)
    private Integer precio;

    private String experiencia;

    private String modalidad = "VIRTUAL";

    private String foto;


    public Tutor() {
    }

    public Tutor(Usuario usuario, Materia materia, String nivel, Integer precio,
                 String experiencia, String modalidad, String foto) {
        this.usuario = usuario;
        this.materia = materia;
        this.nivel = nivel;
        this.precio = precio;
        this.experiencia = experiencia;
        this.modalidad = modalidad != null ? modalidad : "VIRTUAL";
        this.foto = foto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Materia getMateria() { return materia; }
    public void setMateria(Materia materia) { this.materia = materia; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public Integer getPrecio() { return precio; }
    public void setPrecio(Integer precio) { this.precio = precio; }

    public String getExperiencia() { return experiencia; }
    public void setExperiencia(String experiencia) { this.experiencia = experiencia; }

    public String getModalidad() { return modalidad; }
    public void setModalidad(String modalidad) { this.modalidad = modalidad; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }
}