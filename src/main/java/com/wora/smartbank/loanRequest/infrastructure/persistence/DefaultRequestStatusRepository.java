package com.wora.smartbank.loanRequest.infrastructure.persistence;

import com.wora.smartbank.loanRequest.domain.Request;
import com.wora.smartbank.loanRequest.domain.entity.RequestStatus;
import com.wora.smartbank.loanRequest.domain.entity.Status;
import com.wora.smartbank.loanRequest.domain.exception.RequestNotFoundException;
import com.wora.smartbank.loanRequest.domain.exception.StatusNotFoundException;
import com.wora.smartbank.loanRequest.domain.repository.RequestStatusRepository;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestStatusId;
import com.wora.smartbank.orm.internal.DefaultJpaRepository;
import com.wora.smartbank.orm.internal.util.TransactionManager;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class DefaultRequestStatusRepository extends DefaultJpaRepository<RequestStatus, RequestStatusId> implements RequestStatusRepository {

    private static final Logger log = LoggerFactory.getLogger(DefaultRequestStatusRepository.class);

    public DefaultRequestStatusRepository() {
        super(RequestStatus.class, RequestStatusId.class);
    }

    @Override
    public RequestStatus saveWithAssociations(RequestStatus requestStatus) {
        TransactionManager.executeWithoutResult(emf, em -> {
            Status status = em.find(Status.class, requestStatus.status().id());
            if (status == null)
                throw new StatusNotFoundException(requestStatus.status().id());

            Request request = em.find(Request.class, requestStatus.request().id());
            if (request == null)
                throw new RequestNotFoundException(requestStatus.request().id());

            requestStatus.setRequest(request);
            requestStatus.setStatus(status);
            em.persist(requestStatus);
        });
        return requestStatus;
    }
}
