package mx.ipn.media_recomender.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "libros_favoritos")
public class LibroFavorito {
    @Id
    private String libroId; // ID de Google Books API
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    private String titulo;
    private String autores;

    @Column(name = "portada_url", length = 1000)
    private String portadaUrl;
    
    @Column(nullable = false)
    private boolean activo = true;
}