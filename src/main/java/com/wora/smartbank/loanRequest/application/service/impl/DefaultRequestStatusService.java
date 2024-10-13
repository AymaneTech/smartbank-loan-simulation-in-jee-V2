package com.wora.smartbank.loanRequest.application.service.impl;

import com.wora.smartbank.loanRequest.application.dto.RequestStatusDto;
import com.wora.smartbank.loanRequest.application.service.RequestStatusService;
import com.wora.smartbank.loanRequest.domain.Request;
import com.wora.smartbank.loanRequest.domain.entity.RequestStatus;
import com.wora.smartbank.loanRequest.domain.entity.Status;
import com.wora.smartbank.loanRequest.domain.repository.RequestStatusRepository;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestStatusId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class DefaultRequestStatusService implements RequestStatusService {

    private static final Logger log = LoggerFactory.getLogger(DefaultRequestStatusService.class);

    private final RequestStatusRepository repository;

    @Inject
    public DefaultRequestStatusService(RequestStatusRepository repository) {
        this.repository = repository;
    }

    @Override
    public RequestStatus create(RequestStatusDto dto) {
        final RequestStatus requestStatus = new RequestStatus(
                new RequestStatusId(),
                dto.description(),
                new Status().setId(dto.statusId()),
                new Request().setId(dto.requestId())
        );
        return repository.saveWithAssociations(requestStatus);
    }
}
