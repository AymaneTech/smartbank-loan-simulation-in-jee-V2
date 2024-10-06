package com.wora.smartbank.loanRequest.application.service.impl;

import com.wora.smartbank.loanRequest.application.dto.RequestRequest;
import com.wora.smartbank.loanRequest.application.dto.RequestResponse;
import com.wora.smartbank.loanRequest.application.service.RequestService;
import com.wora.smartbank.loanRequest.domain.Request;
import com.wora.smartbank.loanRequest.domain.exception.RequestNotFoundException;
import com.wora.smartbank.loanRequest.domain.repository.RequestRepository;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;

import java.util.List;

@ApplicationScoped
public class DefaultRequestService implements RequestService {

    private final RequestRepository repository;
    private final ModelMapper mapper;

    @Inject
    public DefaultRequestService(RequestRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<RequestResponse> findAll() {
        return repository.findAll().stream()
                .map(r -> mapper.map(r, RequestResponse.class))
                .toList();
    }

    @Override
    public RequestResponse findById(RequestId id) {
        return repository.findById(id)
                .map(r -> mapper.map(r, RequestResponse.class))
                .orElseThrow(() -> new RequestNotFoundException(id));
    }

    @Override
    public RequestResponse create(RequestRequest dto) {
        final Request savedRequest = repository.save(mapper.map(dto, Request.class));
        return mapper.map(savedRequest, RequestResponse.class);
    }

    @Override
    public RequestResponse update(RequestId id, RequestRequest dto) {
        final Request updatedRequest = repository.save(mapper.map(dto, Request.class));
        return mapper.map(updatedRequest, RequestResponse.class);
    }

    @Override
    public void delete(RequestId id) {
        repository.delete(id);
    }

    @Override
    public boolean existsById(RequestId id) {
        return repository.existsById(id);
    }
}
