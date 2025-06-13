package mx.ipn.media_recomender.dto;

import lombok.Data;

@Data
public class BookItemView {
    private String id;
    private String titulo;
    private String autores;
    private String portadaUrl;
    private boolean esFavorito;

    public BookItemView(GoogleBooksResponse.BookItem item, boolean esFavorito) {
        this.id = item.getId();
        this.titulo = item.getVolumeInfo().getTitle();
        this.autores = item.getVolumeInfo().getAuthors() != null ? 
            String.join(", ", item.getVolumeInfo().getAuthors()) : "Autor desconocido";
        this.portadaUrl = item.getVolumeInfo().getImageLinks() != null ? 
            item.getVolumeInfo().getImageLinks().getThumbnail() : "";
        this.esFavorito = esFavorito;
    }
}