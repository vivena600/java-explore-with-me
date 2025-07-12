package ru.practicum;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StatClient {
    private final WebClient webClient;
    private final DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public StatClient(String serverUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(serverUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     * Сохраняет информацию о посещении (хите)
     */
    public HitDto saveHit(HitDto hitDto) {
        return webClient.post()
                .uri("/hit")
                .bodyValue(hitDto)
                .retrieve()
                .bodyToMono(HitDto.class)
                .block();
    }

    /**
     * Получает статистику посещений за указанный период
     */
    public List<StatsDto> getStats(LocalDateTime start, LocalDateTime end,
                                   List<String> uris, Boolean unique) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/stats")
                        .queryParam("start", start.format(dateTimeFormatter))
                        .queryParam("end", end.format(dateTimeFormatter))
                        .queryParam("uri", uris)
                        .queryParam("unique", unique)
                        .build())
                .retrieve()
                .bodyToFlux(StatsDto.class)
                .collectList()
                .block();
    }
}
