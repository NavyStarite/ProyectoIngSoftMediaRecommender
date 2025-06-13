package mx.ipn.media_recomender.service;

import mx.ipn.media_recomender.dto.GoogleBooksResponse;
import mx.ipn.media_recomender.model.LibroFavorito;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GoogleBooksService {
    private final WebClient webClient;
    private final String apiKey;
    private final String apiUrl;

    public GoogleBooksService(
            @Value("${google.books.api.url}") String apiUrl,
            @Value("${google.books.api.key}") String apiKey) {
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("Accept", "application/json")
                .build();
    }

    public Mono<GoogleBooksResponse> buscarLibros(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/volumes")
                        .queryParam("q", query)
                        .queryParam("maxResults", "12")
                        .queryParam("key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(GoogleBooksResponse.class);
    }

    public Mono<GoogleBooksResponse> buscarRecomendaciones(String genero) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/volumes")
                        .queryParam("q", "subject:" + genero)
                        .queryParam("maxResults", "6")
                        .queryParam("orderBy", "newest")
                        .queryParam("key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(GoogleBooksResponse.class);
    }

    public Mono<GoogleBooksResponse.BookItem> obtenerLibroPorId(String id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/volumes/{id}")
                        .queryParam("key", apiKey)
                        .build(id))
                .retrieve()
                .bodyToMono(GoogleBooksResponse.BookItem.class);
    }

    public Mono<GoogleBooksResponse> obtenerRecomendacionesBasadasEnFavoritos(List<LibroFavorito> favoritos) {
        if (favoritos == null || favoritos.isEmpty()) {
            return buscarRecomendaciones("fiction");
        }

        // Extraer autores únicos de los libros favoritos
        List<String> autoresUnicos = favoritos.stream()
            .map(LibroFavorito::getAutores)
            .filter(autores -> autores != null && !autores.isEmpty())
            .flatMap(autores -> Arrays.stream(autores.split(",\\s*")))
            .distinct()
            .limit(3) // Limitar a 3 autores para no hacer la query muy larga
            .collect(Collectors.toList());

        if (autoresUnicos.isEmpty()) {
            return buscarRecomendaciones("fiction");
        }

        // Construir query con los autores
        String queryAutores = String.join("|", autoresUnicos);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/volumes")
                        .queryParam("q", "inauthor:(" + queryAutores + ")")
                        .queryParam("maxResults", "6")
                        .queryParam("key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(GoogleBooksResponse.class);
    }
}