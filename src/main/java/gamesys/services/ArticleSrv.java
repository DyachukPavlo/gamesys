package gamesys.services;

import gamesys.domain.google.Article;
import gamesys.repository.ArticleRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleSrv {
    private final ArticleRepo articleRepo;

    public ArticleSrv(ArticleRepo articleRepo) {
        this.articleRepo = articleRepo;
    }

    public void save(Article article){
        articleRepo.save(article);
    }

    public void save(List<Article> articles){
        articles.forEach(article -> articleRepo.save(article));
    }

    public List findAll(){
        return articleRepo.findAll();
    }

    public List findFew(int quantity) {
        return articleRepo.findLastFew(quantity);
    }
}
