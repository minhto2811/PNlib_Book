package minhtvph26873.poly.pnlib.Interface;

import java.util.List;

public interface InterfaceDAO<T> {
    long insert(T t);

    long delete(String id);

    long update(T t);

    List<T> getAll();

    T getId(String id);

    List<T> getData(String sql, String... selectionArgs);
}
