package mx.ipn.media_recomender.controller;

import mx.ipn.media_recomender.dto.OmdbMovieResponse;
import mx.ipn.media_recomender.model.Usuario;
import mx.ipn.media_recomender.repository.UsuarioRepository;
import mx.ipn.media_recomender.service.OmdbService;
import mx.ipn.media_recomender.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PeliculaFavoritaController {

    private final UsuarioRepository usuarioRepository;
    private final OmdbService omdbService;
    private final PeliculaService peliculaService;

    @Autowired
    public PeliculaFavoritaController(UsuarioRepository usuarioRepository,
                                    OmdbService omdbService,
                                    PeliculaService peliculaService) {
        this.usuarioRepository = usuarioRepository;
        this.omdbService = omdbService;
        this.peliculaService = peliculaService;
    }

    @PostMapping("/favoritos-peliculas/agregar/{imdbId}")
    public String agregarFavorita(@PathVariable String imdbId,
                                @AuthenticationPrincipal UserDetails userDetails,
                                RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = usuarioRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            OmdbMovieResponse pelicula = omdbService.obtenerPeliculaPorId(imdbId)
                .blockOptional()
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));
            
            peliculaService.agregarFavorita(pelicula, usuario);
            redirectAttributes.addFlashAttribute("success", "Película añadida a favoritos");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al añadir a favoritos: " + e.getMessage());
        }
        return "redirect:/dashboard";
    }
    // Agrega este método a PeliculaFavoritaController.java
    @PostMapping("/favoritos-peliculas/eliminar/{imdbId}")
    public String eliminarFavorita(@PathVariable String imdbId,
                                @AuthenticationPrincipal UserDetails userDetails,
                                RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = usuarioRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            peliculaService.eliminarFavorita(imdbId, usuario);
            redirectAttributes.addFlashAttribute("success", "Película eliminada de favoritos");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar de favoritos: " + e.getMessage());
        }
        return "redirect:/dashboard";
    }
}