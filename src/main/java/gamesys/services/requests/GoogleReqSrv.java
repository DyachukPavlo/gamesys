package gamesys.services.requests;

import gamesys.domain.google.GoogleNewsResponse;
import gamesys.services.crud.ArticleSrv;
import gamesys.services.utils.RequestHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleReqSrv implements RequestData<GoogleNewsResponse>{
    private final ArticleSrv articleSrv;

    @Value("${google-news-api}")
    private String googleNewsApiUrl;

    @Value("${google-key}")
    private String googleKey;

    public GoogleReqSrv(ArticleSrv articleSrv) {
        this.articleSrv = articleSrv;
    }

    @Override
    public GoogleNewsResponse sendRequest(){
        List<GoogleNewsResponse> googleNewsResponses = new ArrayList<>();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("sources", "google-news");
        params.add("apiKey", googleKey);
        return RequestHandler.getMono(googleNewsApiUrl, params, GoogleNewsResponse.class).block();
    }

    @Override
    public void saveResponse(GoogleNewsResponse news) {
        articleSrv.save(news.getArticles());
    }
}
