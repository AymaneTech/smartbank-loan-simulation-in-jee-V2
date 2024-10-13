package com.wora.smartbank.loanRequest.application.service.impl;

import com.wora.smartbank.loanRequest.application.dto.RequestStatusRequest;
import com.wora.smartbank.loanRequest.application.service.RequestStatusService;
import com.wora.smartbank.loanRequest.domain.entity.RequestStatus;
import com.wora.smartbank.loanRequest.domain.repository.RequestStatusRepository;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestStatusId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DefaultRequestStatusService implements RequestStatusService {

    private final RequestStatusRepository repository;

    @Inject
    public DefaultRequestStatusService(RequestStatusRepository repository) {
        this.repository = repository;
    }

    @Override
    public RequestStatus save(RequestStatusRequest dto) {
        final RequestStatus requestStatus = new RequestStatus(
                new RequestStatusId(),
                dto.description(),
                dto.status(),
                dto.request()
        );
        return repository.save(requestStatus);
    }
}
