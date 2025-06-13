package mx.ipn.media_recomender.controller;

import mx.ipn.media_recomender.dto.OmdbMovieResponse;
import mx.ipn.media_recomender.model.Usuario;
import mx.ipn.media_recomender.repository.UsuarioRepository;
import mx.ipn.media_recomender.service.OmdbService;
import mx.ipn.media_recomender.service.PeliculaService;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PeliculaController {

    private final OmdbService omdbService;
    private final PeliculaService peliculaService;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public PeliculaController(OmdbService omdbService,
                            PeliculaService peliculaService,
                            UsuarioRepository usuarioRepository) {
        this.omdbService = omdbService;
        this.peliculaService = peliculaService;
        this.usuarioRepository = usuarioRepository;
    }

    // Actualiza PeliculaController.java
    // En PeliculaController.java
    @GetMapping("/buscar-peliculas")
    public String buscarPeliculas(@RequestParam String query,
                                @AuthenticationPrincipal UserDetails userDetails,
                                Model model) {
        try {
            Usuario usuario = usuarioRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            OmdbMovieResponse resultados = omdbService.buscarPeliculas(query)
                .blockOptional()
                .orElse(new OmdbMovieResponse());
            
            if (resultados.getSearch() == null || resultados.getSearch().isEmpty()) {
                model.addAttribute("error", "No se encontraron resultados para: " + query);
                resultados.setSearch(Collections.emptyList()); // Asegurar lista vacía
            }
            
            model.addAttribute("resultados", resultados);
            model.addAttribute("query", query);
            model.addAttribute("usuario", usuario); // Añadir usuario para los botones de favoritos
            
        } catch (Exception e) {
            model.addAttribute("error", "Error en la búsqueda: " + e.getMessage());
        }
        return "busqueda-peliculas";
    }
}