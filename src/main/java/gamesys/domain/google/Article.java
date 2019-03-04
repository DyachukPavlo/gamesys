package gamesys.domain.google;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "articles")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Article implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE", name = "published_at")
    private Timestamp publishedAt;

    @Column(columnDefinition = "VARCHAR(100)")
    private String author;

    @Transient
    private String urlToImage;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Transient
    private Source source;

    @Column(columnDefinition = "VARCHAR(250)")
    private String title;

    @Column(columnDefinition = "VARCHAR(200)")
    private String url;

    @Column(columnDefinition = "TEXT")
    private String content;

    public Timestamp getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Timestamp publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return Objects.equals(getAuthor(), article.getAuthor()) &&
                Objects.equals(getDescription(), article.getDescription()) &&
                Objects.equals(getTitle(), article.getTitle()) &&
                Objects.equals(getUrl(), article.getUrl()) &&
                Objects.equals(getContent(), article.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthor(), getDescription(), getTitle(), getUrl(), getContent());
    }

    @Override
    public String toString() {
        return "GoogleReqSrv{" +
                "id=" + id +
                ", publishedAt=" + publishedAt +
                ", author='" + author + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", description='" + description + '\'' +
                ", source=" + source +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}