package com.wora.smartbank.loanRequest.infrastructure.persistence;

import com.wora.smartbank.loanRequest.domain.Request;
import com.wora.smartbank.loanRequest.domain.repository.RequestRepository;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestId;
import com.wora.smartbank.orm.internal.DefaultJpaRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DefaultRequestRepository extends DefaultJpaRepository<Request, RequestId> implements RequestRepository {

    public DefaultRequestRepository() {
        super(Request.class, RequestId.class);
    }
}
