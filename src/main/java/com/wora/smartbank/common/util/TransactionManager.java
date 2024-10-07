package com.wora.smartbank.common.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

import java.util.function.Consumer;
import java.util.function.Function;

public class TransactionManager {

    public static <T> T executeWithResult(EntityManagerFactory emf, Function<EntityManager, T> executor) {
        try (final EntityManager em = emf.createEntityManager()) {
            final EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                T result = executor.apply(em);
                tx.commit();
                return result;
            } catch (Exception ew) {
                tx.rollback();
                throw new PersistenceException(ew.getMessage());
            } finally {
                em.close();
            }
        }
    }

    public static void executeWithoutResult(EntityManagerFactory emf, Consumer<EntityManager> executor) {
        try (final EntityManager em = emf.createEntityManager()) {
            final EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                executor.accept(em);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw new PersistenceException(e.getMessage());
            } finally {
                em.close();
            }
        }
    }
}
