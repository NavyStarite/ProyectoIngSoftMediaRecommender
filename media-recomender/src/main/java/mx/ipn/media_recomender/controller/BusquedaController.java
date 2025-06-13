package mx.ipn.media_recomender.controller;

import mx.ipn.media_recomender.dto.BookItemView;
import mx.ipn.media_recomender.dto.GoogleBooksResponse;
import mx.ipn.media_recomender.model.Usuario;
import mx.ipn.media_recomender.repository.UsuarioRepository;
import mx.ipn.media_recomender.service.GoogleBooksService;
import mx.ipn.media_recomender.service.LibroService;
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

    @Autowired
    public BusquedaController(GoogleBooksService googleBooksService,
                            LibroService libroService,
                            UsuarioRepository usuarioRepository) {
        this.googleBooksService = googleBooksService;
        this.libroService = libroService;
        this.usuarioRepository = usuarioRepository;
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