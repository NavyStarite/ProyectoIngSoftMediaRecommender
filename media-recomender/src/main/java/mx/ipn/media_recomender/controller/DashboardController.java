package mx.ipn.media_recomender.controller;

import mx.ipn.media_recomender.dto.GoogleBooksResponse;
import mx.ipn.media_recomender.dto.OMDBResponse;
import mx.ipn.media_recomender.model.LibroFavorito;
import mx.ipn.media_recomender.model.PeliculaFavorita;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    private final UsuarioRepository usuarioRepository;
    private final GoogleBooksService googleBooksService;
    private final LibroService libroService;
    private final PeliculaService peliculaService;
    private final OMDBService omdbService;

    @Autowired
    public DashboardController(UsuarioRepository usuarioRepository,
                             GoogleBooksService googleBooksService,
                             LibroService libroService, 
                             PeliculaService peliculaService, 
                             OMDBService omdbService) {
        this.usuarioRepository = usuarioRepository;
        this.googleBooksService = googleBooksService;
        this.libroService = libroService;
        this.peliculaService = peliculaService;
        this.omdbService = omdbService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        try {
            // Verificar que el usuario esté autenticado
            if (userDetails == null) {
                System.err.println("UserDetails is null - user not authenticated");
                return "redirect:/login";
            }

            System.out.println("Dashboard accessed by user: " + userDetails.getUsername());
            
            Usuario usuario = usuarioRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + userDetails.getUsername()));
            
            System.out.println("Usuario encontrado: " + usuario.getUsername());
            
            // Inicializar con listas vacías
            List<LibroFavorito> favoritosLibros = new ArrayList<>();
            List<PeliculaFavorita> favoritosPeliculas = new ArrayList<>();
            
            // Obtener favoritos de forma segura
            try {
                favoritosLibros = libroService.obtenerFavoritosActivos(usuario);
                System.out.println("Libros favoritos obtenidos: " + favoritosLibros.size());
            } catch (Exception e) {
                System.err.println("Error getting favorite books: " + e.getMessage());
                e.printStackTrace();
            }
            
            try {
                favoritosPeliculas = peliculaService.obtenerFavoritosActivos(usuario);
                System.out.println("Películas favoritas obtenidas: " + favoritosPeliculas.size());
            } catch (Exception e) {
                System.err.println("Error getting favorite movies: " + e.getMessage());
                e.printStackTrace();
            }
            
            // Inicializar respuestas vacías
            List<GoogleBooksResponse.BookItem> recomendacionesLibros = new ArrayList<>();
            List<OMDBResponse.MovieItem> recomendacionesPeliculas = new ArrayList<>();
            
            // Obtener recomendaciones de libros
            try {
                GoogleBooksResponse booksResponse = googleBooksService
                    .obtenerRecomendacionesBasadasEnFavoritos(favoritosLibros)
                    .blockOptional()
                    .orElse(new GoogleBooksResponse());
                
                if (booksResponse.getItems() != null) {
                    recomendacionesLibros = booksResponse.getItems().stream()
                        .limit(6)
                        .collect(Collectors.toList());
                }
                System.out.println("Recomendaciones de libros obtenidas: " + recomendacionesLibros.size());
            } catch (Exception e) {
                System.err.println("Error getting book recommendations: " + e.getMessage());
                e.printStackTrace();
            }
            
            // Obtener recomendaciones de películas
            try {
                System.out.println("Iniciando obtención de recomendaciones de películas...");
                OMDBResponse moviesResponse = omdbService
                    .obtenerRecomendacionesBasadasEnFavoritos(favoritosPeliculas)
                    .blockOptional()
                    .orElse(new OMDBResponse());
                
                System.out.println("Respuesta OMDB obtenida. Response: " + moviesResponse.getResponse());
                
                if (moviesResponse.getSearch() != null && !moviesResponse.getSearch().isEmpty()) {
                    recomendacionesPeliculas = moviesResponse.getSearch().stream()
                        .filter(movie -> movie != null && movie.getTitle() != null)
                        .limit(6)
                        .collect(Collectors.toList());
                    System.out.println("Recomendaciones de películas filtradas: " + recomendacionesPeliculas.size());
                } else {
                    System.out.println("No se encontraron películas en la respuesta o la lista está vacía");
                    
                    // Como fallback, intentar obtener algunas películas populares
                    try {
                        System.out.println("Intentando obtener películas populares como fallback...");
                        OMDBResponse fallbackResponse = omdbService
                            .buscarPeliculas("action")
                            .blockOptional()
                            .orElse(new OMDBResponse());
                        
                        if (fallbackResponse.getSearch() != null && !fallbackResponse.getSearch().isEmpty()) {
                            recomendacionesPeliculas = fallbackResponse.getSearch().stream()
                                .filter(movie -> movie != null && movie.getTitle() != null)
                                .limit(4)
                                .collect(Collectors.toList());
                            System.out.println("Películas fallback obtenidas: " + recomendacionesPeliculas.size());
                        }
                    } catch (Exception fallbackEx) {
                        System.err.println("Error en fallback de películas: " + fallbackEx.getMessage());
                    }
                }
                
            } catch (Exception e) {
                System.err.println("Error getting movie recommendations: " + e.getMessage());
                e.printStackTrace();
            }
            
            // Debug: Imprimir información de las recomendaciones de películas
            System.out.println("=== DEBUG RECOMENDACIONES PELÍCULAS ===");
            for (int i = 0; i < recomendacionesPeliculas.size(); i++) {
                OMDBResponse.MovieItem movie = recomendacionesPeliculas.get(i);
                System.out.println("Película " + i + ": " + movie.getTitle() + " (" + movie.getYear() + ")");
            }
            System.out.println("=======================================");
            
            // Agregar atributos al modelo
            model.addAttribute("usuario", usuario);
            model.addAttribute("favoritosLibros", favoritosLibros);
            model.addAttribute("favoritosPeliculas", favoritosPeliculas);
            model.addAttribute("favoritos", favoritosLibros); // Para compatibilidad con el HTML
            model.addAttribute("recomendacionesLibros", recomendacionesLibros);
            model.addAttribute("recomendaciones", recomendacionesLibros); // Para compatibilidad con el HTML
            model.addAttribute("recomendacionesPeliculas", recomendacionesPeliculas);
            
            // Información adicional para debug
            model.addAttribute("totalFavoritosLibros", favoritosLibros.size());
            model.addAttribute("totalFavoritosPeliculas", favoritosPeliculas.size());
            model.addAttribute("totalRecomendacionesLibros", recomendacionesLibros.size());
            model.addAttribute("totalRecomendacionesPeliculas", recomendacionesPeliculas.size());
            
            System.out.println("Dashboard model prepared successfully");
            System.out.println("Total películas recomendadas enviadas al modelo: " + recomendacionesPeliculas.size());
            
            return "dashboard";
            
        } catch (Exception e) {
            System.err.println("Error in dashboard controller: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Error al cargar el dashboard: " + e.getMessage());
            return "error";
        }
    }
}