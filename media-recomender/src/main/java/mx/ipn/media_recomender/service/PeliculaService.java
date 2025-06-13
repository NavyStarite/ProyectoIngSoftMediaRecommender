package mx.ipn.media_recomender.service;

import mx.ipn.media_recomender.dto.OmdbMovieResponse;
import mx.ipn.media_recomender.model.PeliculaFavorita;
import mx.ipn.media_recomender.model.Usuario;
import mx.ipn.media_recomender.repository.PeliculaFavoritaRepository;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Scope("prototype")
public class PeliculaService {
    private final PeliculaFavoritaRepository peliculaFavoritaRepository;

    public PeliculaService(PeliculaFavoritaRepository peliculaFavoritaRepository) {
        this.peliculaFavoritaRepository = peliculaFavoritaRepository;
    }

    @Transactional
    public void agregarFavorita(OmdbMovieResponse pelicula, Usuario usuario) {
        if (!peliculaFavoritaRepository.existsByImdbIdAndUsuario(pelicula.getImdbId(), usuario)) {
            PeliculaFavorita favorita = new PeliculaFavorita();
            favorita.setImdbId(pelicula.getImdbId());
            favorita.setUsuario(usuario);
            favorita.setTitulo(pelicula.getTitle());
            favorita.setAnio(pelicula.getYear());
            favorita.setPosterUrl(pelicula.getPosterUrl());
            favorita.setDirector(pelicula.getDirector());
            favorita.setActores(pelicula.getActors());
            favorita.setGenero(pelicula.getGenre());
            
            peliculaFavoritaRepository.save(favorita);
        }
    }

    // Agrega estos métodos a PeliculaService.java
    @Transactional
    public void eliminarFavorita(String imdbId, Usuario usuario) {
        peliculaFavoritaRepository.findByImdbIdAndUsuario(imdbId, usuario)
            .ifPresent(favorita -> {
                favorita.setActivo(false);
                peliculaFavoritaRepository.save(favorita);
            });
    }

    public boolean esFavorita(String imdbId, Usuario usuario) {
        return peliculaFavoritaRepository.findByImdbIdAndUsuario(imdbId, usuario)
            .map(PeliculaFavorita::isActivo)
            .orElse(false);
    }

    public List<PeliculaFavorita> obtenerFavoritasActivas(Usuario usuario) {
        return peliculaFavoritaRepository.findByUsuarioAndActivoTrue(usuario);
    }
}