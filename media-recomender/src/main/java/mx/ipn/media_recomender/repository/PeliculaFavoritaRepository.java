package mx.ipn.media_recomender.repository;

import mx.ipn.media_recomender.model.PeliculaFavorita;
import mx.ipn.media_recomender.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PeliculaFavoritaRepository extends JpaRepository<PeliculaFavorita, String> {
    List<PeliculaFavorita> findByUsuarioAndActivoTrue(Usuario usuario);
    boolean existsByImdbIdAndUsuario(String imdbId, Usuario usuario);
    Optional<PeliculaFavorita> findByImdbIdAndUsuario(String imdbId, Usuario usuario);
}