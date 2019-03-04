package gamesys.services.requests;

import gamesys.repository.JokeRepo;
import gamesys.services.crud.JokeSrv;
import gamesys.services.utils.StringConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestEntityManager
public class JokeReqSrvTest {
    @TestConfiguration
    static class JokeServiceConfig {
        @Autowired
        EntityManager em;

        @Bean
        JokeRepo jokeRepo(){
            return new JokeRepo(em);
        }

        @Bean
        public JokeSrv jokeSrv(JokeRepo jokeRepo) {
            return new JokeSrv(jokeRepo);
        }

        @Bean
        JokeReqSrv jokeReqSrv(JokeSrv jokeSrv){
            return new JokeReqSrv(jokeSrv);
        }
    }

    @Autowired
    private JokeSrv jokeSrv;

    @Autowired
    private JokeReqSrv jokeReqSrv;

    @Test
    @Rollback
    public void isDataSaved() {
        jokeReqSrv.saveResponse(jokeReqSrv.sendRequest());
        jokeReqSrv.saveResponse(jokeReqSrv.sendRequest());
        assertEquals(2, jokeSrv.findAll().size());
    }


    @Test
    public void isDataValid() {
        String input = jokeReqSrv.sendRequest();
        jokeReqSrv.saveResponse(input);
        assertNotEquals(input, jokeSrv.findFew(1).get(0).getBody());
        assertEquals(StringConverter.process(input), jokeSrv.findFew(1).get(0).getBody());
    }

}