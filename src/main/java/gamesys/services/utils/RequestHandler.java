package gamesys.services.utils;

import gamesys.exceptions.RemoteServerException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

public class RequestHandler {

    private static WebClient create(String url) {
        return org.springframework.web.reactive.function.client.WebClient.builder().baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    private static WebClient.ResponseSpec buildResponseSpec(String url, MultiValueMap<String, String> params){
        return create(url).get().uri(uriBuilder -> uriBuilder.queryParams(params).build())
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(Charset.forName("UTF-8"))
                .retrieve();
    }

    public static <T> Mono<T> getMono(String url, MultiValueMap<String, String> params, Class<T> type){
        return buildResponseSpec(url, params)
                .onStatus(status -> status != HttpStatus.OK,
                          response -> Mono.just(new RemoteServerException("Internal server error")))
                .bodyToMono(type);
    }

    public static <T> Flux<T> getFlux(String url, MultiValueMap<String, String> params, Class<T> type){
        return buildResponseSpec(url,params)
                .onStatus(status -> status != HttpStatus.OK,
                        response -> Mono.just(new RemoteServerException("Internal server error")))
                .bodyToFlux(type);
    }

}
