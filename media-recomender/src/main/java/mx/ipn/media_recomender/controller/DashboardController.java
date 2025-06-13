package mx.ipn.media_recomender.controller;

import mx.ipn.media_recomender.dto.GoogleBooksResponse;
import mx.ipn.media_recomender.dto.OmdbMovieResponse;
import mx.ipn.media_recomender.model.LibroFavorito;
import mx.ipn.media_recomender.model.PeliculaFavorita;
import mx.ipn.media_recomender.model.Usuario;
import mx.ipn.media_recomender.repository.UsuarioRepository;
import mx.ipn.media_recomender.service.GoogleBooksService;
import mx.ipn.media_recomender.service.LibroService;
import mx.ipn.media_recomender.service.OmdbService;
import mx.ipn.media_recomender.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    private final UsuarioRepository usuarioRepository;
    private final GoogleBooksService googleBooksService;
    private final LibroService libroService;
    private final PeliculaService peliculaService;
    private final OmdbService omdbService; // Nuevo campo

    @Autowired
    public DashboardController(UsuarioRepository usuarioRepository,
                             GoogleBooksService googleBooksService,
                             LibroService libroService,
                             PeliculaService peliculaService,
                             OmdbService omdbService) { // Nuevo parámetro
        this.usuarioRepository = usuarioRepository;
        this.googleBooksService = googleBooksService;
        this.libroService = libroService;
        this.peliculaService = peliculaService;
        this.omdbService = omdbService; // Inicialización
    }

    @GetMapping("/dashboard")
    public String showDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        try {
            Usuario usuario = usuarioRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            List<LibroFavorito> favoritos = libroService.obtenerFavoritosActivos(usuario);
            List<PeliculaFavorita> peliculasFavoritas = peliculaService.obtenerFavoritasActivas(usuario);
            
            // Simplificar temporalmente las recomendaciones para pruebas
            GoogleBooksResponse recomendaciones = googleBooksService
            .obtenerRecomendacionesBasadasEnFavoritos(favoritos)
            .blockOptional()
            .orElse(new GoogleBooksResponse());
        
            // Si no hay suficientes recomendaciones, agregar algunas generales
            if(recomendaciones.getItems() == null || recomendaciones.getItems().size() < 6) {
                GoogleBooksResponse librosGenerales = googleBooksService
                    .buscarRecomendaciones("fiction")
                    .blockOptional()
                    .orElse(new GoogleBooksResponse());
                
                if(recomendaciones.getItems() == null) {
                    recomendaciones.setItems(librosGenerales.getItems());
                } else {
                    recomendaciones.getItems().addAll(librosGenerales.getItems());
                }
            }
            
            // Limitar a 6 recomendaciones para la vista
            List<GoogleBooksResponse.BookItem> recomendacionesLimitadas = recomendaciones.getItems().stream()
                .limit(6)
                .collect(Collectors.toList());
                
            model.addAttribute("usuario", usuario);
            model.addAttribute("favoritos", favoritos);
            model.addAttribute("peliculasFavoritas", peliculasFavoritas);
            
            // Temporarily disable API calls for debugging
            model.addAttribute("recomendaciones", recomendacionesLimitadas);
            model.addAttribute("recomendacionesPeliculas", Collections.emptyList());
            
            return "dashboard";
        } catch (Exception e) {
            // Log the error and redirect to error page
            return "redirect:/error?message=" + e.getMessage();
        }
    }
}