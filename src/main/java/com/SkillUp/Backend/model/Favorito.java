package com.SkillUp.Backend.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity //REPRESENTA LA ENTIDAD PERSISTENTE
@Table(name = "favorito") //LA RELACIONAMOS CON LA TABLA favorito
@Getter
@Setter
@NoArgsConstructor

/*esta clase permite que JPA relacione los elementos
(FavoritoId, Usuario, RecursoBiblioteca) y los persista en la tabla favorito de MySQL*/
public class Favorito {

    @EmbeddedId // le indica a JPA que el atributo id contiene la clave primaria de la entidad FavoritoId y que esta definida mediante una clase @Embeddable
    private FavoritoId id;

    // relacion de muchos a uno, un usuario puede tener muchos favoritos
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUsuario") //le indica a JPA que la relacion con usuario utiliza el atributo idUsuario de la clave primaria compuesta
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idRecurso")
    @JoinColumn(name = "id_recurso", nullable = false)
    private RecursoBiblioteca recurso;

    @Column( name = "fecha_agregado", insertable = false, updatable = false)
    private LocalDateTime fechaAgregado;
}
