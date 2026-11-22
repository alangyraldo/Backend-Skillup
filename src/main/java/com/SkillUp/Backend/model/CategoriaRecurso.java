package com.SkillUp.Backend.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // indica que la clase representa una tabla de la BD
@Table(name = "categoria_recurso") // especifica que se relaciona con la tabla categoria_recurso
@Getter // anotacion lombok, metodo para consultar atributos
@Setter // anotacion lombok, metodo para modificar atributos
@NoArgsConstructor // genera un constructor vacio que necesita JPA
public class CategoriaRecurso {

    @Id // identifica la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //permite que el motor de la BD genere el ID automaticamente
    private Long id;

    @Column(nullable = false, length = 100) // crea una columna y establece sus caracteristicas
    private String nombre;

    /*Ej, cuando creamos una categoria de nombre Ebooks o Guias, MySQL asiganra el ID*/
}
