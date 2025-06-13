package mx.ipn.media_recomender.service;

import mx.ipn.media_recomender.dto.OMDBResponse.MovieItem;
import mx.ipn.media_recomender.model.PeliculaFavorita;
import mx.ipn.media_recomender.model.Usuario;
import mx.ipn.media_recomender.repository.PeliculaFavoritaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaService {
    private final PeliculaFavoritaRepository peliculaFavoritaRepository;

    public PeliculaService(PeliculaFavoritaRepository peliculaFavoritaRepository) {
        this.peliculaFavoritaRepository = peliculaFavoritaRepository;
    }

    public List<PeliculaFavorita> obtenerFavoritosActivos(Usuario usuario) {
        return peliculaFavoritaRepository.findByUsuarioAndActivoTrue(usuario);
    }

    @Transactional
    public void agregarFavorito(MovieItem pelicula, Usuario usuario) {
        if (!peliculaFavoritaRepository.existsByImdbIdAndUsuario(pelicula.getImdbId(), usuario)) {
            PeliculaFavorita favorita = new PeliculaFavorita();
            favorita.setImdbId(pelicula.getImdbId());
            favorita.setUsuario(usuario);
            favorita.setTitulo(pelicula.getTitle());
            favorita.setAnio(pelicula.getYear());
            favorita.setPosterUrl(pelicula.getPoster());
            favorita.setDirector(pelicula.getDirector());
            favorita.setActores(pelicula.getActors());
            favorita.setGenero(pelicula.getGenre());
            
            peliculaFavoritaRepository.save(favorita);
        }
    }

    @Transactional
    public void eliminarFavorito(String imdbId, Usuario usuario) {
        peliculaFavoritaRepository.findByImdbIdAndUsuario(imdbId, usuario)
            .ifPresent(favorita -> {
                favorita.setActivo(false);
                peliculaFavoritaRepository.save(favorita);
            });
    }

    public boolean esFavorito(String imdbId, Usuario usuario) {
        return peliculaFavoritaRepository.findByImdbIdAndUsuario(imdbId, usuario)
            .map(PeliculaFavorita::isActivo)
            .orElse(false);
    }
}