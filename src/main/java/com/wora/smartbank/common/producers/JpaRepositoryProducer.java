package com.wora.smartbank.common.producers;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;

public class JpaRepositoryProducer {
    @Inject
    private EntityManagerFactory emf;

//    @Produces
//    @Dependent
//    @JPA
//    @SuppressWarnings("unchecked")
//    public <T, ID> JpaRepository<T, ID> createJpaRepository(InjectionPoint injectionPoint) {
//        ParameterizedType type = (ParameterizedType) injectionPoint.getType();
//        Class<T> entityClass = (Class<T>) type.getActualTypeArguments()[0];
//        Class<ID> idClass = (Class<ID>) type.getActualTypeArguments()[1];
//
//        return new DefaultJpaRepository<T, ID>(emf, entityClass, idClass);
//    }
}
