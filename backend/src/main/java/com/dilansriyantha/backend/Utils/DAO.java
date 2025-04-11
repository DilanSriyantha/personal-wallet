package com.dilansriyantha.backend.Utils;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    List<T> getAll();
    List<T> getPage(int page, int pageSize);
    Optional<T> get(int id);
    void create(T t);
    void update(int id, T t);
    void delete(int id);
}
