package gamesys.services.crud;

import java.util.List;

public interface CustomCrudSrv<T> {
    public void save(T t);
    public void save(List<T> t);
    public List<T> findAll();
    public List<T> findFew(int quantity);
}
