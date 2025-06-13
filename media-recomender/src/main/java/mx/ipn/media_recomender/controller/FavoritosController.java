package mx.ipn.media_recomender.controller;

import mx.ipn.media_recomender.dto.GoogleBooksResponse;
import mx.ipn.media_recomender.dto.GoogleBooksResponse.BookItem;
import mx.ipn.media_recomender.model.Usuario;
import mx.ipn.media_recomender.repository.UsuarioRepository;
import mx.ipn.media_recomender.service.GoogleBooksService;
import mx.ipn.media_recomender.service.LibroService;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FavoritosController {

    private final UsuarioRepository usuarioRepository;
    private final GoogleBooksService googleBooksService;
    private final LibroService libroService;

    @Autowired
    public FavoritosController(UsuarioRepository usuarioRepository,
                             GoogleBooksService googleBooksService,
                             LibroService libroService) {
        this.usuarioRepository = usuarioRepository;
        this.googleBooksService = googleBooksService;
        this.libroService = libroService;
    }

    @PostMapping("/favoritos/agregar/{libroId}")
    public String agregarFavorito(@PathVariable String libroId,
                                @AuthenticationPrincipal UserDetails userDetails,
                                RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = usuarioRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            BookItem libro = googleBooksService.obtenerLibroPorId(libroId)
                .blockOptional()
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
            
            // Validar URL antes de guardar
            if (libro.getVolumeInfo().getImageLinks() != null && 
                libro.getVolumeInfo().getImageLinks().getThumbnail() != null &&
                libro.getVolumeInfo().getImageLinks().getThumbnail().length() > 1000) {
                redirectAttributes.addFlashAttribute("warning", 
                    "La imagen del libro no se pudo guardar completamente debido a su tamaño");
            }
            
            libroService.agregarFavorito(libro, usuario);
            redirectAttributes.addFlashAttribute("success", "Libro añadido a favoritos");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error al añadir a favoritos: " + e.getMessage());
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/favoritos/eliminar/{libroId}")
    public String eliminarFavorito(@PathVariable String libroId,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = usuarioRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            libroService.eliminarFavorito(libroId, usuario);
            redirectAttributes.addFlashAttribute("success", "Libro eliminado de favoritos");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar de favoritos: " + e.getMessage());
        }
        return "redirect:/dashboard";
    }
}