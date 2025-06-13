package mx.ipn.media_recomender.dto;

import mx.ipn.media_recomender.dto.OMDBResponse.MovieItem;

public class MovieItemView {
    private final MovieItem movieItem;
    private final boolean esFavorita;

    public MovieItemView(MovieItem movieItem, boolean esFavorita) {
        this.movieItem = movieItem;
        this.esFavorita = esFavorita;
    }

    // Delegación de métodos de MovieItem
    public String getImdbId() {
        return movieItem.getImdbId();
    }

    public String getTitle() {
        return movieItem.getTitle();
    }

    public String getYear() {
        return movieItem.getYear();
    }

    public String getPoster() {
        return movieItem.getPoster();
    }

    public String getDirector() {
        return movieItem.getDirector();
    }

    public String getActors() {
        return movieItem.getActors();
    }

    public String getGenre() {
        return movieItem.getGenre();
    }

    // Método para verificar si es favorita
    public boolean isEsFavorita() {
        return esFavorita;
    }

    public boolean getEsFavorita() {
        return esFavorita;
    }

    // Getter para el objeto completo si es necesario
    public MovieItem getMovieItem() {
        return movieItem;
    }
}