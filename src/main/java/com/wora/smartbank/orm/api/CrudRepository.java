package com.wora.smartbank.orm.api;

import java.util.Optional;

public interface CrudRepository<T, ID> {
    T save(T entity);

    Optional<T> findById(ID ids);

    void delete(T entity);

    void deleteById(ID id);

    boolean exists(T entity);

    boolean existsById(ID id);

    long count();
}
