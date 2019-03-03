package gamesys.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

public interface CustomCrudRepo<T> {
    List<T> findAll();
    void save(T t);
    List<T> findLastFew(int quantity);
}
