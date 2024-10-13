package com.wora.smartbank.loanRequest.infrastructure.persistence;

import com.wora.smartbank.loanRequest.domain.entity.RequestStatus;
import com.wora.smartbank.loanRequest.domain.repository.RequestStatusRepository;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestStatusId;
import com.wora.smartbank.orm.internal.DefaultJpaRepository;

public class DefaultRequestStatusRepository extends DefaultJpaRepository<RequestStatus, RequestStatusId> implements RequestStatusRepository {

    public DefaultRequestStatusRepository() {
        super(RequestStatus.class, RequestStatusId.class);
    }
}
