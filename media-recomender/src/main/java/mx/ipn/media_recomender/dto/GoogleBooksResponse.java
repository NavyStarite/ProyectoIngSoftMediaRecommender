package mx.ipn.media_recomender.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleBooksResponse {
    private List<BookItem> items;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BookItem {
        private String id; // Este campo es esencial
        private VolumeInfo volumeInfo;
         private boolean esFavorito;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VolumeInfo {
        private String title;
        private List<String> authors;
        private String description;
        private String publisher;
        private String publishedDate;
        private ImageLinks imageLinks;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ImageLinks {
        private String thumbnail;
    }
}