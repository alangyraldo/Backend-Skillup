package com.SkillUp.Backend.service;

import com.SkillUp.Backend.model.CategoriaRecurso; //entidad que representa la tabla
import com.SkillUp.Backend.repository.CategoriaRecursoRepository; //el repositorio que nos permite acceder a la tabla
import org.springframework.http.HttpStatus; //codigos de estado HTTP como 200,404 o 500
import org.springframework.stereotype.Service; //identifica la clase como componente de servicio de spring
import org.springframework.transaction.annotation.Transactional;//gestiona transacciones con la BD
import org.springframework.web.server.ResponseStatusException; //lanzar excepciones a un codigo HTTP
import java.util.List;

/*anotacion de spring que identifica la clase como un componente de la capa de servicios
Spring detecta la clase cuando inicia la app y crea un objeto administrado por su contenedor (Spreng Bean)
permitiendo inyectar CategoriaRecursoService en otras clases ej, en nuestro controller
* */
@Service
public class CategoriaRecursoService {
    /*inyectamos dependencias y el constructor
    * declaramos una variable que representa nuestro repositorio
    * final: este objeto es inmutable, se crean copias delobjeto pero su clase no es inmutable,
    * su referencia debe asignarse una sola vez, lo usamos porque queremos que el repositorio se asigne al contruir el objeto
    * y que posteriormente no podamos reemplazar su referencia por otro repositorio
    * CategoriaRecursoRepository, es el tipo de variable y categoriaRepository es el nombre que utilizamos para acceder al repositorio
    * el constructor recibe una instancia de CategoriaRecursoRepository, Spring se encraga de proporcionarla automaticamente mediante
    * inyeccion de dependencias
    * */
    private final CategoriaRecursoRepository categoriaRepository;

    public CategoriaRecursoService(
            CategoriaRecursoRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    /*metodo que permite consultar todas las categorias registradas
    *@Transactional
    *
    * */
    @Transactional(readOnly = true)
    public List<CategoriaRecurso> listarCategorias() {
        return categoriaRepository.findAll();
    }

    // Consultar una categoría por su ID
    @Transactional(readOnly = true)
    public CategoriaRecurso obtenerCategoria(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categoría no encontrada con ID: " + id
                ));
    }

    // Crear una categoría
    @Transactional
    public CategoriaRecurso crearCategoria(CategoriaRecurso categoria) {
        return categoriaRepository.save(categoria);
    }

    // Actualizar una categoría
    @Transactional
    public CategoriaRecurso actualizarCategoria(
            Long id,
            CategoriaRecurso datosCategoria) {

        CategoriaRecurso categoria = obtenerCategoria(id);

        categoria.setNombre(datosCategoria.getNombre());

        return categoriaRepository.save(categoria);
    }

    // Eliminar una categoría
    @Transactional
    public void eliminarCategoria(Long id) {

        CategoriaRecurso categoria = obtenerCategoria(id);

        categoriaRepository.delete(categoria);
    }
}
