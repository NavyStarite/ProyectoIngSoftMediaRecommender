package mx.ipn.media_recomender.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "peliculas_favoritas")
public class PeliculaFavorita {
    @Id
    private String imdbId;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    private String titulo;
    private String anio;
    private String posterUrl;
    private String director;
    private String actores;
    private String genero;
    
    @Column(nullable = false)
    private boolean activo = true;
}