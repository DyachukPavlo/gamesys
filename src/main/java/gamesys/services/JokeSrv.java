package gamesys.services;

import gamesys.domain.jokes.Joke;
import gamesys.repository.JokeRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JokeSrv {
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

    public List findFew(int quantity) {
        return jokeRepo.findLastFew(quantity);
    }
}
