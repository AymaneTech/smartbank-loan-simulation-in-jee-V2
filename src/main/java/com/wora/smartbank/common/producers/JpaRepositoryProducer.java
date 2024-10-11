package com.wora.smartbank.common.producers;

import com.wora.smartbank.orm.api.JpaRepository;
import com.wora.smartbank.orm.api.annotation.JPA;
import com.wora.smartbank.orm.internal.DefaultJpaRepository;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;

import java.lang.reflect.ParameterizedType;

public class JpaRepositoryProducer {
    @Inject
    private EntityManagerFactory emf;

    @Produces
    @Dependent
    @JPA
    @SuppressWarnings("unchecked")
    public <T, ID> JpaRepository<T, ID> createJpaRepository(InjectionPoint injectionPoint) {
        ParameterizedType type = (ParameterizedType) injectionPoint.getType();
        Class<T> entityClass = (Class<T>) type.getActualTypeArguments()[0];
        Class<ID> idClass = (Class<ID>) type.getActualTypeArguments()[1];

        return new DefaultJpaRepository<T, ID>(emf, entityClass, idClass);
    }
}
