package DAO;

import java.util.List;

public interface CrudDAO<T> {
    T add(T t);
    List<T> readAll();
    void update(T t);
//    T readOne(T t); //todo delete or no
}
