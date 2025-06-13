package mx.ipn.media_recomender.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OMDBResponse {
    
    @JsonProperty("Search")
    private List<MovieItem> search;
    
    @JsonProperty("totalResults")
    private String totalResults;
    
    @JsonProperty("Response")
    private String response;
    
    @JsonProperty("Error")
    private String error;

    // Constructores
    public OMDBResponse() {
    }

    // Getters y Setters
    public List<MovieItem> getSearch() {
        return search;
    }

    public void setSearch(List<MovieItem> search) {
        this.search = search;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MovieItem {
        
        @JsonProperty("Title")
        private String title;
        
        @JsonProperty("Year")
        private String year;
        
        @JsonProperty("imdbID")
        private String imdbId;
        
        @JsonProperty("Type")
        private String type;
        
        @JsonProperty("Poster")
        private String poster;
        
        // Campos adicionales para detalles completos de película
        @JsonProperty("Plot")
        private String plot;
        
        @JsonProperty("Director")
        private String director;
        
        @JsonProperty("Actors")
        private String actors;
        
        @JsonProperty("Genre")
        private String genre;
        
        @JsonProperty("Runtime")
        private String runtime;
        
        @JsonProperty("imdbRating")
        private String imdbRating;
        
        @JsonProperty("Released")
        private String released;

        // Constructores
        public MovieItem() {
        }

        // Getters y Setters
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getImdbId() {
            return imdbId;
        }

        public void setImdbId(String imdbId) {
            this.imdbId = imdbId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public String getPlot() {
            return plot;
        }

        public void setPlot(String plot) {
            this.plot = plot;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String getActors() {
            return actors;
        }

        public void setActors(String actors) {
            this.actors = actors;
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        public String getRuntime() {
            return runtime;
        }

        public void setRuntime(String runtime) {
            this.runtime = runtime;
        }

        public String getImdbRating() {
            return imdbRating;
        }

        public void setImdbRating(String imdbRating) {
            this.imdbRating = imdbRating;
        }

        public String getReleased() {
            return released;
        }

        public void setReleased(String released) {
            this.released = released;
        }

        @Override
        public String toString() {
            return "MovieItem{" +
                    "title='" + title + '\'' +
                    ", year='" + year + '\'' +
                    ", imdbId='" + imdbId + '\'' +
                    ", type='" + type + '\'' +
                    ", poster='" + poster + '\'' +
                    '}';
        }
    }
}