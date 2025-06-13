package mx.ipn.media_recomender.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OmdbMovieResponse {
    @JsonProperty("Title")
    private String Title;
    
    @JsonProperty("Year")
    private String Year;
    
    @JsonProperty("imdbID")
    private String imdbId;
    
    @JsonProperty("Type")
    private String type;
    
    @JsonProperty("Poster")
    private String posterUrl;
    
    @JsonProperty("Director")
    private String director;
    
    @JsonProperty("Actors")
    private String actors;
    
    @JsonProperty("Genre")
    private String genre;

   @JsonProperty("Search")
    private List<MovieItem> search;
    
    @JsonProperty("totalResults")
    private String totalResults;
    
    @JsonProperty("Response")
    private String response;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MovieItem {
        @JsonProperty("Title")
        private String Title;  // Cambiar a mayúscula para coincidir con JSON
        
        @JsonProperty("Year")
        private String Year;
        
        @JsonProperty("imdbID")
        private String imdbID;
        
        @JsonProperty("Type")
        private String Type;
        
        @JsonProperty("Poster")
        private String Poster;  // Cambiar a mayúscula
        
        @JsonProperty("Genre")
        private String Genre;
}
}
