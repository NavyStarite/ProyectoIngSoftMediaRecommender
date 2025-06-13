package mx.ipn.media_recomender.service;

import mx.ipn.media_recomender.dto.OmdbMovieResponse;
import mx.ipn.media_recomender.model.PeliculaFavorita;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OmdbService {
    private final WebClient webClient;
    private final String apiKey;

    public OmdbService(
            @Value("${omdb.api.url}") String apiUrl,
            @Value("${omdb.api.key}") String apiKey) {
        this.apiKey = apiKey;
        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("Accept", "application/json")
                .build();
    }

    // En OmdbService.java
        public Mono<OmdbMovieResponse> buscarPeliculas(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("s", query)
                        .queryParam("type", "movie")
                        .queryParam("apikey", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(OmdbMovieResponse.class)
                .doOnNext(response -> {
                        if ("False".equals(response.getResponse())) {
                        throw new RuntimeException("Error en la API: " );
                        }
                });
        }

    public Mono<OmdbMovieResponse> obtenerPeliculaPorId(String imdbId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("i", imdbId)
                        .queryParam("apikey", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(OmdbMovieResponse.class);
    }

    // Agrega este método a OmdbService.java
        public Mono<OmdbMovieResponse> buscarRecomendaciones(String genero) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("s", genero)
                        .queryParam("type", "movie")
                        .queryParam("apikey", apiKey)
                        .queryParam("y", "2020-2023") // Películas recientes
                        .build())
                .retrieve()
                .bodyToMono(OmdbMovieResponse.class);
        }

        public Mono<OmdbMovieResponse> obtenerRecomendacionesBasadasEnFavoritos(List<PeliculaFavorita> favoritos) {
        if (favoritos == null || favoritos.isEmpty()) {
                return buscarRecomendaciones("action"); // Género por defecto
        }

        // Extraer géneros más comunes de las películas favoritas
        String generoMasComun = favoritos.stream()
                .filter(p -> p.getGenero() != null)
                .flatMap(p -> Arrays.stream(p.getGenero().split(",\\s*")))
                .collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("action");

        return buscarRecomendaciones(generoMasComun);
        }
}