package com.SkillUp.Backend.model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable // indica que esta clase representa una clave que se incorpora a otra entidad, representa la clave primaria compuesta
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor // constructor con todos los atributos

/*
Se crea la clase independiente para representar en Java la combinacion de identificadores, asi que los representamos
en un solo objeto, representa la combinacion de id_usuario y de id_recurso
* Serializable, permite que el objeto pueda serializarse(transformarse en una secuencia de bytes o en un formato de texto (JSON), para ser guardados o enviados),
 es un requisito para claves compuestas JPA
* */
public class FavoritoId implements Serializable {
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_recurso")
    private Long idRecurso;

    @Override
    public boolean equals(Object o) { // compara las dos claves para determinar si son iguales
        if (this == o) return true;
        if (!(o instanceof FavoritoId that)) return false;

        return Objects.equals(idUsuario, that.idUsuario) && //Objects, proporciona metodos para comparar objetos y calcular sus codigos hash
                Objects.equals(idRecurso, that.idRecurso);
    }
    @Override
    public int hashCode() { // genera un codigo consistente con la igualdad del objeto
        return Objects.hash(idUsuario, idRecurso);
    }

}
