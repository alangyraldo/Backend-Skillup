package com.SkillUp.Backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.SkillUp.Backend.util.FotoUtils;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", nullable = false)
    private TipoUsuario tipoUsuario;

    // WRITE_ONLY: se acepta al recibir el JSON, pero nunca se devuelve en las respuestas
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String contrasena;

    @Column(length = 20)
    private String telefono;

    @JsonIgnore
    @Lob
    @Column(name = "foto", columnDefinition = "MEDIUMBLOB")
    private byte[] fotoBytes;

    @JsonIgnore
    @Column(name = "foto_tipo", length = 50)
    private String fotoTipo;

    /** Mantiene el contrato JSON actual del frontend; la BD almacena bytes BLOB. */
    public String getFoto() {
        return FotoUtils.aDataUrl(fotoBytes, fotoTipo);
    }

    public void setFoto(String dataUrl) {
        FotoUtils.Foto foto = FotoUtils.desdeDataUrl(dataUrl);
        this.fotoBytes = foto == null ? null : foto.bytes();
        this.fotoTipo = foto == null ? null : foto.contentType();
    }

    // La asigna MySQL (DEFAULT CURRENT_TIMESTAMP)
    @Column(name = "fecha_registro", insertable = false, updatable = false)
    private LocalDateTime fechaRegistro;
}