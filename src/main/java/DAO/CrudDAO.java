package DAO;

import Exceptions.SomeThingWrongWithBDException;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> {
    T add(T t) throws SQLException;
    List<T> readAll() throws SomeThingWrongWithBDException;
}
