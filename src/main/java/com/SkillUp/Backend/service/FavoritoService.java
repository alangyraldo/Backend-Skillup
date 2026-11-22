
package com.SkillUp.Backend.service;

import com.SkillUp.Backend.model.Favorito;
import com.SkillUp.Backend.model.FavoritoId;
import com.SkillUp.Backend.model.RecursoBiblioteca;
import com.SkillUp.Backend.model.Usuario;

import com.SkillUp.Backend.repository.FavoritoRepository;
import com.SkillUp.Backend.repository.RecursoBibliotecaRepository;
import com.SkillUp.Backend.repository.UsuarioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final RecursoBibliotecaRepository recursoRepository;

    public FavoritoService(
            FavoritoRepository favoritoRepository,
            UsuarioRepository usuarioRepository,
            RecursoBibliotecaRepository recursoRepository) {

        this.favoritoRepository = favoritoRepository;
        this.usuarioRepository = usuarioRepository;
        this.recursoRepository = recursoRepository;
    }

    // Consultar favoritos de un usuario
    @Transactional(readOnly = true)
    public List<Favorito> listarFavoritos(Long idUsuario) {

        if (!usuarioRepository.existsById(idUsuario)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Usuario no encontrado con ID: " + idUsuario
            );
        }

        return favoritoRepository.findById_IdUsuario(idUsuario);
    }

    // Agregar un recurso a favoritos
    @Transactional
    public Favorito agregarFavorito(Long idUsuario, Long idRecurso) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario no encontrado con ID: " + idUsuario
                ));

        RecursoBiblioteca recurso = recursoRepository.findById(idRecurso)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Recurso no encontrado con ID: " + idRecurso
                ));

        FavoritoId favoritoId = new FavoritoId(idUsuario, idRecurso);

        if (favoritoRepository.existsById(favoritoId)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Este recurso ya está en los favoritos del usuario"
            );
        }

        Favorito favorito = new Favorito();

        favorito.setId(favoritoId);
        favorito.setUsuario(usuario);
        favorito.setRecurso(recurso);

        return favoritoRepository.save(favorito);
    }

    // Eliminar un recurso de favoritos
    @Transactional
    public void eliminarFavorito(Long idUsuario, Long idRecurso) {

        FavoritoId favoritoId = new FavoritoId(idUsuario, idRecurso);

        if (!favoritoRepository.existsById(favoritoId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El favorito solicitado no existe"
            );
        }

        favoritoRepository.deleteById(favoritoId);
    }
}
