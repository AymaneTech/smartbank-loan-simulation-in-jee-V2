package com.wora.smartbank.orm.internal;

import com.wora.smartbank.common.util.TransactionManager;
import com.wora.smartbank.orm.api.JpaRepository;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public abstract class DefaultJpaRepository<T, ID> implements JpaRepository<T, ID> {

    private static final Logger log = LoggerFactory.getLogger(DefaultJpaRepository.class);
    private final Class<T> entityClass;
    private final EntityDetails entityDetails;
    @Inject
    private EntityManagerFactory emf;

    protected DefaultJpaRepository() {
        this.entityClass = getEntityClass();
        this.entityDetails = getEntityDetails();
    }

    @SuppressWarnings("unchecked")
    private Class<T> getEntityClass() {
        ParameterizedType genericsSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) genericsSuperclass.getActualTypeArguments()[0];
    }

    @Override
    public T save(T entity) {
        log.info("Saving entity to database");
        TransactionManager.executeWithoutResult(emf, em -> em.persist(entity));
        return entity;
    }

    @Override
    public List<T> saveAll(List<T> entities) {
        log.info("Saving entity list");
        TransactionManager.executeWithoutResult(emf, em -> entities
                .forEach(this::save));
        return entities;
    }

    @Override
    public Optional<T> findById(ID id) {
        log.info("Fetching from {} by id", entityDetails.tableName());
        return TransactionManager.executeWithResult(emf,
                em -> Optional.ofNullable(em.find(entityClass, id)));

    }

    @Override
    public List<T> findAllById(List<ID> ids) {
        log.info("fetching all entities by id");
        final Set<ID> uniqueIds = new HashSet<>(ids);
        if (uniqueIds.isEmpty())
            throw new IllegalArgumentException("ids list to find with is empty");

        return TransactionManager.executeWithResult(emf, em -> {
            final CriteriaQuery<T> query = em.getCriteriaBuilder()
                    .createQuery(entityClass);
            final Root<T> root = query.from(entityClass);
            query.where(root.get("id").in(uniqueIds));
            return em.createQuery(query).getResultList();
        });
    }

    @Override
    public List<T> findAll() {
        log.info("fetching all entities");
        return TransactionManager.executeWithResult(emf, em -> {
            CriteriaQuery<T> query = em.getCriteriaBuilder()
                    .createQuery(entityClass);
            query.from(entityClass);
            return em.createQuery(query).getResultList();
        });
    }

    @Override
    public void delete(T entity) {
        log.info("Deleting from {} ", entityDetails.tableName());
        TransactionManager.executeWithoutResult(emf, em -> em.remove(em.merge(entity)));
    }

    @Override
    public void deleteById(ID id) {
        log.info("Deleting from {} by id", entityDetails.tableName());
        TransactionManager.executeWithoutResult(emf, em -> findById(id).ifPresent(em::remove));
    }

    @Override
    public void deleteAll(List<ID> ids) {
        log.info("Deleting all entities");
        TransactionManager.executeWithoutResult(emf, em -> findAllById(ids)
                .forEach(em::remove));
    }

    @Override
    public boolean exists(T entity) {
        log.info("Checking if {} exists}", entityDetails.tableName());
        return TransactionManager.executeWithResult(emf, em ->
                em.contains(entity) || em.find(entityClass, resloveEntityId(entity)) != null
        );
    }

    @Override
    public boolean existsById(ID id) {
        return TransactionManager.executeWithResult(emf, em -> {
            final CriteriaBuilder cb = em.getCriteriaBuilder();
            final CriteriaQuery<Long> query = cb.createQuery(Long.class);
            final Root<T> root = query.from(entityClass);
            query.select(cb.count(root)).where(cb.equal(root.get("id"), id));
            return em.createQuery(query).getSingleResult() > 0;
        });
    }

    @Override
    public long count() {
        log.info("counting rows of entity {}", entityDetails.name());
        return TransactionManager.executeWithResult(emf, em -> {
            final CriteriaBuilder cb = em.getCriteriaBuilder();
            final CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<T> root = query.from(entityClass);
            query.select(cb.count(root));
            return em.createQuery(query).getSingleResult();
        });
    }

    private EntityDetails getEntityDetails() {
        return new EntityDetails(entityClass.getSimpleName(), resolveTableName());
    }


    @SuppressWarnings("unchecked")
    private ID resloveEntityId(T entity) {
        try {
            return (ID) entityClass.getMethod("id")
                    .invoke(entity);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    private String resolveTableName() {
        return entityClass.isAnnotationPresent(Table.class)
                ? entityClass.getAnnotation(Table.class).name()
                : toSnakeCase(entityClass.getSimpleName());
    }

    // todo: move this helper to some other place
    private String toSnakeCase(String str) {
        if (str == null)
            throw new IllegalArgumentException("the string to convert to snake case is null");
        return str.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }

    record EntityDetails(String name, String tableName) {

    }

}
