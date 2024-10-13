package com.wora.smartbank.loanRequest.infrastructure.persistence;

import com.wora.smartbank.loanRequest.domain.entity.Status;
import com.wora.smartbank.loanRequest.domain.repository.StatusRepository;
import com.wora.smartbank.loanRequest.domain.valueObject.StatusId;
import com.wora.smartbank.orm.internal.DefaultJpaRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DefaultStatusRepository extends DefaultJpaRepository<Status, StatusId> implements StatusRepository {

    public DefaultStatusRepository() {
        super(Status.class, StatusId.class);
    }

}
