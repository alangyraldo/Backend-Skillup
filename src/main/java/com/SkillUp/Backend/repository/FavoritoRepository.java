package com.SkillUp.Backend.repository;
import com.SkillUp.Backend.model.Favorito;
import com.SkillUp.Backend.model.FavoritoId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

//este repository es diferente porque la clave primaria de la clase Favorito no es un Long sino un objeto FavoritoId, que contiene los dos campos de la clave compuesta
public interface FavoritoRepository
        extends JpaRepository<Favorito, FavoritoId> {

    /*permite recuperar en una lista los favoritos de un usuario especifico
    * findBy, busca
    * Id, accede al atributo id de favorito
    * _, navega en el objeto
    * Idusuario, accede al atributo idusuario de FavoritoId*/
    List<Favorito> findById_IdUsuario(Long idUsuario);
}
