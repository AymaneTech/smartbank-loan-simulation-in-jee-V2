package com.wora.smartbank.orm.internal.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.function.Function;

public class TransactionManager {

    private static final Logger log = LoggerFactory.getLogger(TransactionManager.class);

    public static <T> T executeWithResult(EntityManagerFactory emf, Function<EntityManager, T> executor) {
        try (final EntityManager em = emf.createEntityManager()) {
            final EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                T result = executor.apply(em);
                tx.commit();
                return result;
            } catch (Exception e) {
                log.error("Error during transaction execution", e);
                if (tx.isActive()) {
                    log.info("Rolling back transaction");
                    tx.rollback();
                }
                throw e;
            }
        }
    }

    public static void executeWithoutResult(EntityManagerFactory emf, Consumer<EntityManager> executor) {
        try (final EntityManager em = emf.createEntityManager()) {
            final EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                executor.accept(em);
                log.info("Before committing");
                tx.commit();
                log.info("After committing");
            } catch (Exception e) {
                log.error("Error during transaction execution", e);
                if (tx.isActive()) {
                    log.info("Rolling back transaction");
                    tx.rollback();
                }
                throw e;
            } finally {
                em.close();
            }
        }
    }
}
