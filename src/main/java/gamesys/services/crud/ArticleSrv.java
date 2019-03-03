package gamesys.services.crud;

import gamesys.domain.google.Article;
import gamesys.repository.ArticleRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleSrv implements CustomCrudSrv<Article>{
    private final ArticleRepo articleRepo;

    public ArticleSrv(ArticleRepo articleRepo) {
        this.articleRepo = articleRepo;
    }

    public void save(Article article){
        articleRepo.save(article);
    }

    public void save(List<Article> articles){
        articles.forEach(articleRepo::save);
    }

    public List<Article> findAll(){
        return articleRepo.findAll();
    }

    public List<Article> findFew(int quantity) {
        return articleRepo.findLastFew(quantity);
    }
}
