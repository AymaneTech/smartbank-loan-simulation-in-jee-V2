package com.wora.smartbank.common.producers;

import com.wora.smartbank.orm.api.JpaRepository;
import com.wora.smartbank.orm.internal.DefaultJpaRepository;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;

import java.lang.reflect.ParameterizedType;

public class JpaRepositoryProducer {

    @Produces
    @SuppressWarnings("unchecked")
    public <T, ID> JpaRepository<T, ID> createJpaRepository(InjectionPoint injectionPoint) {
        ParameterizedType type = (ParameterizedType) injectionPoint.getType();
        Class<T> entityClass = (Class<T>) type.getActualTypeArguments()[0];
        return new DefaultJpaRepository<T, ID>() {
        };
    }
}
