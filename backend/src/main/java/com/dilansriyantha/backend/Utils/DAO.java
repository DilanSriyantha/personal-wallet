package com.dilansriyantha.backend.Utils;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

public interface DAO<T> {
    List<T> getAll();
    Page<T> getPage(int page, int pageSize);
    Optional<T> get(int id);
    void create(T object);
    void update(int id, T newDetails);
    void delete(int id);
}
