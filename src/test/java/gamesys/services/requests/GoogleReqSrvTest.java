package gamesys.services.requests;

import gamesys.domain.google.Article;
import gamesys.domain.google.GoogleNewsResponse;
import gamesys.repository.ArticleRepo;
import gamesys.services.crud.ArticleSrv;
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

import java.util.List;

import static org.junit.Assert.*;

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

    @Test
    @Rollback
    public void isDataSaved() {
        googleReqSrv.saveResponse(googleReqSrv.sendRequest());
        assertNotNull(articleSrv.findAll().size());
    }

    @Test
    @Rollback
    public void dataWasNotLost() {
        GoogleNewsResponse googleNewsResponse = googleReqSrv.sendRequest();
        List<Article> articles = googleNewsResponse.getArticles();
        googleReqSrv.saveResponse(googleReqSrv.sendRequest());
        assertEquals(articles.size(), articleSrv.findAll().size());
    }

    @Test
    public void isDataValid() {
        GoogleNewsResponse googleNewsResponse = googleReqSrv.sendRequest();
        List<Article> articles = googleNewsResponse.getArticles();
        googleReqSrv.saveResponse(googleNewsResponse);
        assertEquals(articles, articleSrv.findAll());
    }


}