package com.wora.smartbank.orm.api;

import java.util.List;

public interface ListCrudRepository<T, ID> extends CrudRepository<T, ID> {
    List<T> findAll();

    List<T> findAllById(List<ID> ids);

    List<T> saveAll(List<T> entities);

    void deleteAll(List<ID> ids);
}
