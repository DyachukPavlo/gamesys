package gamesys.services.requests;

import gamesys.repository.ArticleRepo;
import gamesys.services.crud.ArticleSrv;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestEntityManager
public class GoogleReqSrvTest {
    @TestConfiguration
    static class JokeServiceConfig {
        @Autowired
        EntityManager em;

        @Bean
        ArticleRepo articleRepo(){
            return new ArticleRepo(em);
        }

        @Bean
        public ArticleSrv articleSrv(ArticleRepo articleRepo) {
            return new ArticleSrv(articleRepo);
        }

        @Bean
        GoogleReqSrv googleReqSrv(ArticleSrv articleSrv){
            return new GoogleReqSrv(articleSrv);
        }
    }

    @Autowired
    private ArticleSrv articleSrv;

    @Autowired
    private GoogleReqSrv googleReqSrv;

}