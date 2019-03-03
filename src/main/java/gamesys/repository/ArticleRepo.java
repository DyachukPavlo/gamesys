package gamesys.repository;

import gamesys.domain.google.Article;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ArticleRepo implements CustomCrudRepo<Article>{
    private final EntityManager entityManager;

    public ArticleRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void save(Article article) {
        entityManager.createNativeQuery("insert into articles (author, title, url, content, description, published_at) " +
                " values (:author, :title, :url, :content, :description, :published_at)")
                .setParameter("author", article.getAuthor())
                .setParameter("title", article.getTitle())
                .setParameter("url", article.getUrl())
                .setParameter("content", article.getContent())
                .setParameter("description", article.getDescription())
                .setParameter("published_at", article.getPublishedAt())
                .executeUpdate();
        entityManager.close();
    }

    public List<Article> findAll() {
        return entityManager.createNativeQuery("select * from articles", Article.class).getResultList();
    }

    public List<Article> findLastFew(int quantity) {
        return entityManager.createNativeQuery("select TOP :quantity * from articles order by id desc", Article.class)
                .setParameter("quantity", quantity).getResultList();
    }
}