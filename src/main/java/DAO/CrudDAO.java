package DAO;

import Exceptions.SomeThingWrongWithBDException;

import java.util.List;

public interface CrudDAO<T> {
    T add(T t);
    List<T> readAll() throws SomeThingWrongWithBDException;
}
