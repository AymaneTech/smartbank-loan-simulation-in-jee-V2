package com.wora.smartbank.common.producers;

import com.wora.smartbank.config.DefaultPersistenceUnitInfo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;

public class EntityManagerFactoryProducer {

    private EntityManagerFactory factory;

    @Produces
    @ApplicationScoped
    public EntityManagerFactory createEntityManagerFactory() {
        if (factory == null) {
            factory = new HibernatePersistenceProvider()
                    .createContainerEntityManagerFactory(new DefaultPersistenceUnitInfo(), null);
        }
        return factory;
    }
}
