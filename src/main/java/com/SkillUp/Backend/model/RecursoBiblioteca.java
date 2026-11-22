package com.SkillUp.Backend.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "recurso_biblioteca")
@Getter
@Setter
@NoArgsConstructor
public class RecursoBiblioteca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*creamos la relacion
    * en SQL tenemos:
        FOREIGN KEY (id_categoria)
        REFERENCES categoria_recurso(id)
        ON DELETE CASCADE
    esto quiere decir que cada recurso pertenece a una categoria.
    *
    * @ManyToOne, porque una categoria puede tener muchos recursos, pero cada recurso pertenece
    a una categoria
    *  @JoinColumn, le indica a JPA que columna de la tabla actual contiene la clave foranea
    * que permite relacionar las dos entidades, resumen: indica donde esta la clave foranea que conecta las dos entidades
    *
    * FetchType, es una configuracion que determina la estrategia de craga de las entidades
    * relacionadas (LAZY, permite aplazar la recuperacion de la entidad relacionada hasta que se accede a ella)
    * columnDefinition, Le indica a Hibernate el tipo SQL que debe utilizar cuando genera el esquema de la base de datos
    *
    * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaRecurso categoria;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(name = "tipo_recurso", nullable = false, length = 50)
    private String tipoRecurso;

    @Column(name = "url_archivo", length = 255)
    private String urlArchivo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

}
