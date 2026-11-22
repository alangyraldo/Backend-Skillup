package com.SkillUp.Backend.service;

import com.SkillUp.Backend.dto.FavoritoRequest;
import com.SkillUp.Backend.dto.FavoritoResponse;

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

    // Listar favoritos de un usuario
    @Transactional(readOnly = true)
    public List<FavoritoResponse> listarFavoritos(Long idUsuario) {

        if (!usuarioRepository.existsById(idUsuario)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Usuario no encontrado con ID: " + idUsuario
            );
        }

        return favoritoRepository.findById_IdUsuario(idUsuario)
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    // Agregar un recurso a favoritos
    @Transactional
    public FavoritoResponse agregarFavorito(FavoritoRequest request) {

        Usuario usuario = usuarioRepository.findById(request.idUsuario())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario no encontrado con ID: " + request.idUsuario()
                ));

        RecursoBiblioteca recurso = recursoRepository.findById(request.idRecurso())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Recurso no encontrado con ID: " + request.idRecurso()
                ));

        FavoritoId favoritoId = new FavoritoId(
                request.idUsuario(),
                request.idRecurso()
        );

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

        Favorito guardado = favoritoRepository.save(favorito);

        return convertirAResponse(guardado);
    }

    // Eliminar un favorito
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

    // Convertir entidad a DTO
    private FavoritoResponse convertirAResponse(Favorito favorito) {

        return new FavoritoResponse(
                favorito.getUsuario().getId(),
                favorito.getRecurso().getId(),
                favorito.getRecurso().getTitulo(),
                favorito.getFechaAgregado()
        );
    }
}
