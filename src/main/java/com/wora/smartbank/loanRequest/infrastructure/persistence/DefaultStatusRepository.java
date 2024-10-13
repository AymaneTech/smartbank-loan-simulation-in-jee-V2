package com.wora.smartbank.loanRequest.infrastructure.persistence;

import com.wora.smartbank.loanRequest.domain.entity.Status;
import com.wora.smartbank.loanRequest.domain.repository.StatusRepository;
import com.wora.smartbank.loanRequest.domain.valueObject.StatusId;
import com.wora.smartbank.orm.internal.DefaultJpaRepository;
import com.wora.smartbank.orm.internal.util.TransactionManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@ApplicationScoped
public class DefaultStatusRepository extends DefaultJpaRepository<Status, StatusId> implements StatusRepository {

    private static final Logger log = LoggerFactory.getLogger(DefaultStatusRepository.class);

    public DefaultStatusRepository() {
        super(Status.class, StatusId.class);
    }

    @Override
    public Optional<Status> findByName(String value) {
        log.info("findByName( {} ) invoked in {}", value, getClass().getSimpleName());
        return TransactionManager.executeWithResult(emf, em -> {
            TypedQuery<Status> query = em.createQuery("SELECT s FROM Status s WHERE s.name = :name", Status.class);
            query.setParameter("name", value);
            return Optional.ofNullable(query.getSingleResult());
        });
    }
}
