package com.SkillUp.Backend.repository;

import com.SkillUp.Backend.model.RecursoBiblioteca;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

//funcion: consulta los recursos segun su categoria
public interface RecursoBibliotecaRepository
extends JpaRepository<RecursoBiblioteca, Long>{
    /*consultamos los recursos que pertenecen a una categoria especifica
    * findBy, busca los registros que cumplan la condicion
    * Categoria, atributo categoria de RecursoBiblioteca
    * _, navega a un atributo de la entidad relacionada
    * Id, identificador de la categoria
    * */
    List<RecursoBiblioteca> findByCategoria_Id(Long idCategoria);
}
