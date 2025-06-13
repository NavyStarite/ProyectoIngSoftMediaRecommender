package mx.ipn.media_recomender.repository;

import mx.ipn.media_recomender.model.LibroFavorito;
import mx.ipn.media_recomender.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroFavoritoRepository extends JpaRepository<LibroFavorito, String> {
    List<LibroFavorito> findByUsuarioAndActivoTrue(Usuario usuario);
    boolean existsByLibroIdAndUsuario(String libroId, Usuario usuario);
    
    // Método nuevo que necesitamos
    Optional<LibroFavorito> findByLibroIdAndUsuario(String libroId, Usuario usuario);
}