package com.solvd.spotify.domain.repositories;

import java.util.List;

public interface JdbcRepository<T, ID> {

    T findById(ID id);

    List<T> findAll();

    void create(T entity);

    void update(T entity);

    void delete(ID id);

}
