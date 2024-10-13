package com.wora.smartbank.loanRequest.infrastructure.persistence;

import com.wora.smartbank.loanRequest.domain.Request;
import com.wora.smartbank.loanRequest.domain.entity.RequestStatus;
import com.wora.smartbank.loanRequest.domain.entity.Status;
import com.wora.smartbank.loanRequest.domain.repository.RequestRepository;
import com.wora.smartbank.loanRequest.domain.repository.StatusRepository;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestId;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestStatusId;
import com.wora.smartbank.orm.internal.DefaultJpaRepository;
import com.wora.smartbank.orm.internal.util.TransactionManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class DefaultRequestRepository extends DefaultJpaRepository<Request, RequestId> implements RequestRepository {

    private static final Logger log = LoggerFactory.getLogger(DefaultRequestRepository.class);

    private final StatusRepository statusRepository;

    @Inject
    public DefaultRequestRepository(StatusRepository statusRepository) {
        super(Request.class, RequestId.class);
        this.statusRepository = statusRepository;
    }

    @Override
    public Request saveRequestWithDefaultStatus(Request request) {
        TransactionManager.executeWithoutResult(emf, em -> {
            Status status = getDefaultStatus(em);

            RequestStatus requestStatus = new RequestStatus(
                    new RequestStatusId(),
                    "this request's status is sets to pending until get reviewed by the team",
                    status,
                    request
            );

            request.addRequestStatus(requestStatus);
            em.persist(request);

        });
        return request;
    }

    private Status getDefaultStatus(EntityManager em) {
        return em.createQuery("SELECT s FROM Status s WHERE s.name = :name", Status.class)
                .setParameter("name", "PENDING")
                .getSingleResult();
    }
}
