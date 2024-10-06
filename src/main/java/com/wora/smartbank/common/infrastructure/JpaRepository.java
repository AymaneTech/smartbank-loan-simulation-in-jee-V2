package com.wora.smartbank.common.infrastructure;

import java.util.List;
import java.util.Optional;

public interface JpaRepository<Entity, ID> {
    List<Entity> findAll();

    Optional<Entity> findById(ID id);

    Entity save(Entity entity);

    void delete(ID id);

    boolean existsById(ID id);

}
