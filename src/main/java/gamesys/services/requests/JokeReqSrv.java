package gamesys.services.requests;

import gamesys.domain.jokes.Joke;
import gamesys.services.crud.JokeSrv;
import gamesys.services.utils.RequestHandler;
import gamesys.services.utils.StringConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JokeReqSrv implements RequestData<String>{

    private final JokeSrv jokeSrv;

    @Value("${joke-api}")
    private String jokeApiUrl;

    public JokeReqSrv(JokeSrv jokeSrv) {
        this.jokeSrv = jokeSrv;
    }

    @Override
    public String sendRequest(){
        return RequestHandler.getMono(jokeApiUrl, null, String.class).block();
    }

    @Override
    public void saveResponse(String text){
        text = StringConverter.process(text);
        jokeSrv.save(new Joke(text));
    }
}
