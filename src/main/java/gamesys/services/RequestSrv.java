package gamesys.services;

import gamesys.exceptions.RemoteServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

@Service
public class RequestSrv {

    private WebClient create(String url) {
        return org.springframework.web.reactive.function.client.WebClient.builder().baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public <T> Mono<T> sendGetMono(String url, Class<T> type){
        return create(url).get()//.uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(Charset.forName("UTF-8"))
                .retrieve()
                .onStatus(status -> status != HttpStatus.OK,
                          response -> Mono.just(new RemoteServerException("Internal server error")))
                .bodyToMono(type);
    }

    public <T> Flux<T> sendGetFlux(String url, MultiValueMap<String, String> params, Class<T> type){
        return create(url).get().uri(uriBuilder -> uriBuilder.queryParams(params).build())
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(Charset.forName("UTF-8"))
                .retrieve()
                .onStatus(status -> status != HttpStatus.OK,
                        response -> Mono.just(new RemoteServerException("Internal server error")))
                .bodyToFlux(type);
    }
}
