package mx.ipn.media_recomender.controller;

import mx.ipn.media_recomender.dto.BookItemView;
import mx.ipn.media_recomender.dto.GoogleBooksResponse;
import mx.ipn.media_recomender.dto.MovieItemView;
import mx.ipn.media_recomender.dto.OMDBResponse;
import mx.ipn.media_recomender.model.Usuario;
import mx.ipn.media_recomender.repository.UsuarioRepository;
import mx.ipn.media_recomender.service.GoogleBooksService;
import mx.ipn.media_recomender.service.LibroService;
import mx.ipn.media_recomender.service.OMDBService;
import mx.ipn.media_recomender.service.PeliculaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BusquedaController {

    private final GoogleBooksService googleBooksService;
    private final LibroService libroService;
    private final UsuarioRepository usuarioRepository;
    private final OMDBService omdbService;
    private final PeliculaService peliculaService;

    @Autowired
    public BusquedaController(GoogleBooksService googleBooksService,
                            LibroService libroService,
                            UsuarioRepository usuarioRepository, 
                            OMDBService omdbService,
                            PeliculaService peliculaService) {
        this.googleBooksService = googleBooksService;
        this.libroService = libroService;
        this.usuarioRepository = usuarioRepository;
        this.omdbService = omdbService;
        this.peliculaService = peliculaService;
    }

    @GetMapping("/buscar/peliculas")
    public String buscarPeliculas(@RequestParam String query,
                                @AuthenticationPrincipal UserDetails userDetails,
                                Model model) {
        try {
            System.out.println("Buscando películas para: " + query);
            
            Usuario usuario = usuarioRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            OMDBResponse resultados = omdbService.buscarPeliculas(query)
                .blockOptional()
                .orElse(new OMDBResponse());
            
            System.out.println("Respuesta de OMDB: " + resultados.getResponse());
            
            List<MovieItemView> resultadosProcesados = Collections.emptyList();
            
            if (resultados.getSearch() != null && !resultados.getSearch().isEmpty()) {
                resultadosProcesados = resultados.getSearch().stream()
                    .map(pelicula -> new MovieItemView(
                        pelicula, 
                        peliculaService.esFavorito(pelicula.getImdbId(), usuario)
                    ))
                    .collect(Collectors.toList());
                
                System.out.println("Películas procesadas: " + resultadosProcesados.size());
            } else {
                System.out.println("No se encontraron películas o la respuesta fue negativa");
            }
            
            model.addAttribute("resultadosPeliculas", resultadosProcesados);
            model.addAttribute("query", query);
            
        } catch (Exception e) {
            System.err.println("Error en la búsqueda de películas: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Error en la búsqueda de películas: " + e.getMessage());
            model.addAttribute("resultadosPeliculas", Collections.emptyList());
        }
        
        return "busqueda-peliculas";
    }

    @GetMapping("/buscar")
    public String buscarLibros(@RequestParam String query,
                             @AuthenticationPrincipal UserDetails userDetails,
                             Model model) {
        try {
            Usuario usuario = usuarioRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            GoogleBooksResponse resultados = googleBooksService.buscarLibros(query)
                .blockOptional()
                .orElse(new GoogleBooksResponse());
            
            List<BookItemView> resultadosProcesados = resultados.getItems() != null ?
                resultados.getItems().stream()
                    .map(item -> new BookItemView(
                        item, 
                        libroService.esFavorito(item.getId(), usuario)
                    ))
                    .collect(Collectors.toList()) :
                Collections.emptyList();
            
            model.addAttribute("resultados", resultadosProcesados);
            model.addAttribute("query", query);
            
        } catch (Exception e) {
            model.addAttribute("error", "Error en la búsqueda: " + e.getMessage());
            model.addAttribute("resultados", Collections.emptyList());
        }
        
        return "busqueda";
    }
}