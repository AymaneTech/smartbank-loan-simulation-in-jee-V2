package com.wora.smartbank.orm.api;

import java.util.Optional;

public interface CrudRepository<T, ID> {
    T save(T entity);

    T update(T entity);

    Optional<T> findById(ID ids);

    <V> Optional<T> findByColumn(String columnName, V value);

    void delete(T entity);

    void deleteById(ID id);

    boolean exists(T entity);

    boolean existsById(ID id);

    long count();
}
