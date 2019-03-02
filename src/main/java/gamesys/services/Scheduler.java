package gamesys.services;

import gamesys.domain.google.GoogleNewsResponse;
import gamesys.domain.jokes.Joke;
import gamesys.utils.StringConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Component
public class Scheduler {
    private final JokeSrv jokeSrv;
    private final ArticleSrv articleSrv;
    private final RequestSrv webClient;

    @Value("${joke-api}")
    private String jokeApiUrl;

    @Value("${google-news-api}")
    private String googleNewsApiUrl;

    @Value("${google-key}")
    private String googleKey;

    public Scheduler(JokeSrv jokeSrv, ArticleSrv articleSrv, RequestSrv webClient) {
        this.jokeSrv = jokeSrv;
        this.articleSrv = articleSrv;
        this.webClient = webClient;
    }

    @PostConstruct
    private void performTask() {
        ScheduledExecutorService executorServiceJokes = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService executorServiceNews = Executors.newSingleThreadScheduledExecutor();
        executorServiceJokes.scheduleAtFixedRate(this::findJoke, 0, 5, TimeUnit.SECONDS);
        executorServiceNews.scheduleAtFixedRate(this::findNews, 0, 15, TimeUnit.MINUTES);
    }

    private void findJoke() {
        Mono<String> response = webClient.sendGetMono(jokeApiUrl, String.class);
        response = response.map(StringConverter::process);
        response.subscribe(result->jokeSrv.save(new Joke(result)));
    }

    private void findNews(){
        List<GoogleNewsResponse> googleNewsResponses = new ArrayList<>();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("sources", "google-news");
        params.add("apiKey", googleKey);
        Flux<GoogleNewsResponse> response = webClient.sendGetFlux(googleNewsApiUrl, params, GoogleNewsResponse.class);
        response.subscribe(result->articleSrv.save(result.getArticles()));
    }
}