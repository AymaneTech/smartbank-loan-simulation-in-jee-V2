package com.wora.smartbank.loanRequest.application.service.impl;

import com.wora.smartbank.loanRequest.application.service.RequestStatusService;
import com.wora.smartbank.loanRequest.domain.Request;
import com.wora.smartbank.loanRequest.domain.entity.RequestStatus;
import com.wora.smartbank.loanRequest.domain.entity.Status;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestStatusId;
import com.wora.smartbank.orm.api.JpaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DefaultRequestStatusService implements RequestStatusService {

    private final JpaRepository<RequestStatus, RequestStatusId> repository;

    @Inject
    public DefaultRequestStatusService(JpaRepository<RequestStatus, RequestStatusId> repository) {
        this.repository = repository;
    }

    @Override
    public RequestStatus save(Request request, Status status) {
        final RequestStatus requestStatus = new RequestStatus(
                new RequestStatusId(),
                status,
                request
        );
        return repository.save(requestStatus);
    }
}
