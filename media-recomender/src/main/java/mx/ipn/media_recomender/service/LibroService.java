package mx.ipn.media_recomender.service;

import mx.ipn.media_recomender.dto.GoogleBooksResponse.BookItem;
import mx.ipn.media_recomender.model.LibroFavorito;
import mx.ipn.media_recomender.model.Usuario;
import mx.ipn.media_recomender.repository.LibroFavoritoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {
    private final LibroFavoritoRepository libroFavoritoRepository;

    public LibroService(LibroFavoritoRepository libroFavoritoRepository) {
        this.libroFavoritoRepository = libroFavoritoRepository;
    }

    public List<LibroFavorito> obtenerFavoritosActivos(Usuario usuario) {
        return libroFavoritoRepository.findByUsuarioAndActivoTrue(usuario);
    }

    @Transactional
    public void agregarFavorito(BookItem libro, Usuario usuario) {
        if (!libroFavoritoRepository.existsByLibroIdAndUsuario(libro.getId(), usuario)) {
            LibroFavorito favorito = new LibroFavorito();
            favorito.setLibroId(libro.getId());
            favorito.setUsuario(usuario);
            favorito.setTitulo(libro.getVolumeInfo().getTitle());
            
            // Manejo seguro de autores
            if (libro.getVolumeInfo().getAuthors() != null) {
                favorito.setAutores(String.join(", ", libro.getVolumeInfo().getAuthors()));
            } else {
                favorito.setAutores("Autor desconocido");
            }
            
            // Manejo seguro de la URL de la portada
            if (libro.getVolumeInfo().getImageLinks() != null && 
                libro.getVolumeInfo().getImageLinks().getThumbnail() != null) {
                
                // Acortar URL si es necesario
                String thumbnailUrl = libro.getVolumeInfo().getImageLinks().getThumbnail();
                if (thumbnailUrl.length() > 1000) {
                    thumbnailUrl = thumbnailUrl.substring(0, 1000);
                }
                favorito.setPortadaUrl(thumbnailUrl);
            }
            
            libroFavoritoRepository.save(favorito);
        }
    }

    @Transactional
    public void eliminarFavorito(String libroId, Usuario usuario) {
        libroFavoritoRepository.findByLibroIdAndUsuario(libroId, usuario)
            .ifPresent(favorito -> {
                favorito.setActivo(false);
                libroFavoritoRepository.save(favorito);
            });
    }

    @Transactional
    public void toggleFavorito(String libroId, BookItem libro, Usuario usuario) {
        if (libroId == null || libro == null || usuario == null) {
            throw new IllegalArgumentException("Parámetros no pueden ser nulos");
        }
        
        Optional<LibroFavorito> existente = libroFavoritoRepository.findByLibroIdAndUsuario(libroId, usuario);
        
        if (existente.isPresent()) {
            LibroFavorito favorito = existente.get();
            favorito.setActivo(!favorito.isActivo());
            libroFavoritoRepository.save(favorito);
        } else {
            agregarFavorito(libro, usuario);
        }
    }

    public boolean esFavorito(String libroId, Usuario usuario) {
        return libroFavoritoRepository.findByLibroIdAndUsuario(libroId, usuario)
            .map(LibroFavorito::isActivo)
            .orElse(false);
    }
}