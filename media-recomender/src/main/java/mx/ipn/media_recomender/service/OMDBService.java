package mx.ipn.media_recomender.service;

import mx.ipn.media_recomender.dto.OMDBResponse;
import mx.ipn.media_recomender.model.PeliculaFavorita;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Random;

@Service
public class OMDBService {
    private final WebClient webClient;
    private final String apiKey;
    private final String apiUrl;
    private final Random random = new Random();

    // Géneros populares para recomendaciones por defecto
    private final String[] generosPopulares = {
        "action", "comedy", "drama", "thriller", "horror", 
        "adventure", "romance", "sci-fi", "fantasy", "mystery"
    };

    public OMDBService(
            @Value("${omdb.api.url}") String apiUrl,
            @Value("${omdb.api.key}") String apiKey) {
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("Accept", "application/json")
                .build();
    }

    public Mono<OMDBResponse> buscarPeliculas(String query) {
        System.out.println("Buscando películas con query: " + query);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("s", query)
                        .queryParam("apikey", apiKey)
                        .queryParam("type", "movie")
                        .build())
                .retrieve()
                .bodyToMono(OMDBResponse.class)
                .doOnNext(response -> {
                    System.out.println("Respuesta OMDB: " + response.getResponse());
                    if (response.getSearch() != null) {
                        System.out.println("Películas encontradas: " + response.getSearch().size());
                    }
                })
                .onErrorResume(WebClientResponseException.class, ex -> {
                    System.err.println("Error HTTP en OMDB: " + ex.getStatusCode() + " - " + ex.getMessage());
                    return Mono.just(new OMDBResponse());
                })
                .onErrorResume(Exception.class, ex -> {
                    System.err.println("Error general en OMDB: " + ex.getMessage());
                    return Mono.just(new OMDBResponse());
                });
    }

    public Mono<OMDBResponse.MovieItem> obtenerPeliculaPorId(String id) {
        System.out.println("Obteniendo película por ID: " + id);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("i", id)
                        .queryParam("apikey", apiKey)
                        .queryParam("plot", "short")
                        .build())
                .retrieve()
                .bodyToMono(OMDBResponse.MovieItem.class)
                .doOnNext(movie -> System.out.println("Película obtenida: " + movie.getTitle()))
                .onErrorResume(WebClientResponseException.class, ex -> {
                    System.err.println("Error HTTP obteniendo película por ID: " + ex.getStatusCode());
                    return Mono.empty();
                })
                .onErrorResume(Exception.class, ex -> {
                    System.err.println("Error general obteniendo película por ID: " + ex.getMessage());
                    return Mono.empty();
                });
    }

    public Mono<OMDBResponse> obtenerRecomendacionesBasadasEnFavoritos(List<PeliculaFavorita> favoritos) {
        System.out.println("Obteniendo recomendaciones basadas en " + favoritos.size() + " favoritos");
        
        if (favoritos == null || favoritos.isEmpty()) {
            System.out.println("No hay favoritos, usando géneros populares");
            return obtenerRecomendacionesPorGenero(generosPopulares[random.nextInt(generosPopulares.length)]);
        }

        // Extraer géneros únicos de los favoritos
        List<String> generosUnicos = favoritos.stream()
            .map(PeliculaFavorita::getGenero)
            .filter(g -> g != null && !g.isEmpty())
            .flatMap(g -> Arrays.stream(g.split(",\\s*")))
            .map(String::toLowerCase)
            .map(String::trim)
            .distinct()
            .limit(3) // Limitar a 3 géneros para mejor variedad
            .collect(Collectors.toList());

        System.out.println("Géneros extraídos: " + generosUnicos);

        if (generosUnicos.isEmpty()) {
            System.out.println("No se encontraron géneros válidos, usando géneros populares");
            return obtenerRecomendacionesPorGenero(generosPopulares[random.nextInt(generosPopulares.length)]);
        }

        // Seleccionar un género aleatorio de los favoritos
        String generoSeleccionado = generosUnicos.get(random.nextInt(generosUnicos.size()));
        System.out.println("Género seleccionado para recomendaciones: " + generoSeleccionado);
        
        return obtenerRecomendacionesPorGenero(generoSeleccionado)
            .flatMap(response -> {
                // Si no hay resultados con el primer género, intentar con otro
                if (response.getSearch() == null || response.getSearch().isEmpty()) {
                    if (generosUnicos.size() > 1) {
                        String segundoGenero = generosUnicos.stream()
                            .filter(g -> !g.equals(generoSeleccionado))
                            .findFirst()
                            .orElse(generosPopulares[random.nextInt(generosPopulares.length)]);
                        System.out.println("Intentando con segundo género: " + segundoGenero);
                        return obtenerRecomendacionesPorGenero(segundoGenero);
                    }
                }
                return Mono.just(response);
            });
    }

    private Mono<OMDBResponse> obtenerRecomendacionesPorGenero(String genero) {
        // Crear diferentes consultas para obtener variedad
        String[] consultas = {
            genero,
            genero + " 2020",
            genero + " 2021",
            genero + " 2022",
            genero + " movie",
            "best " + genero
        };
        
        String consultaSeleccionada = consultas[random.nextInt(consultas.length)];
        System.out.println("Buscando con consulta: " + consultaSeleccionada);
        
        return buscarPeliculas(consultaSeleccionada)
            .flatMap(response -> {
                // Si no hay resultados, intentar con una consulta más simple
                if (response.getSearch() == null || response.getSearch().isEmpty()) {
                    System.out.println("No hay resultados, intentando consulta simple: " + genero);
                    return buscarPeliculas(genero);
                }
                return Mono.just(response);
            })
            .onErrorResume(e -> {
                System.err.println("Error obteniendo recomendaciones por género: " + e.getMessage());
                return Mono.just(new OMDBResponse());
            });
    }
}