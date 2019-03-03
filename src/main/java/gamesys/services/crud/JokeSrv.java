package gamesys.services.crud;

import gamesys.domain.jokes.Joke;
import gamesys.repository.JokeRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JokeSrv implements CustomCrudSrv<Joke>{
    private final JokeRepo jokeRepo;

    public JokeSrv(JokeRepo jokeRepo) {
        this.jokeRepo = jokeRepo;
    }

    public List<Joke> findAll(){
        return jokeRepo.findAll();
    }

    public void save(Joke joke){
        jokeRepo.save(joke);
    }

    public void save(List<Joke> jokes){
        jokes.forEach(jokeRepo::save);
    }

    public List<Joke> findFew(int quantity) {
        return jokeRepo.findLastFew(quantity);
    }
}
