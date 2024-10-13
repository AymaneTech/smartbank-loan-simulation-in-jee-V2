package com.wora.smartbank.loanRequest.application.service.impl;

import com.wora.smartbank.common.domain.exception.InvalidRequestException;
import com.wora.smartbank.common.domain.valueObject.ValidationErrors;
import com.wora.smartbank.loanRequest.application.dto.StatusRequest;
import com.wora.smartbank.loanRequest.application.dto.StatusResponse;
import com.wora.smartbank.loanRequest.application.service.StatusService;
import com.wora.smartbank.loanRequest.domain.entity.Status;
import com.wora.smartbank.loanRequest.domain.exception.StatusNotFoundException;
import com.wora.smartbank.loanRequest.domain.repository.StatusRepository;
import com.wora.smartbank.loanRequest.domain.valueObject.StatusId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Set;

@ApplicationScoped
public class DefaultStatusService implements StatusService {

    private final StatusRepository repository;
    private final ModelMapper mapper;
    private final Validator validator;

    @Inject
    public DefaultStatusService(StatusRepository repository, ModelMapper mapper, Validator validator) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Override
    public List<StatusResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    public StatusResponse findById(StatusId id) {
        return repository.findById(id)
                .map(this::toResponseDto)
                .orElseThrow(() -> new StatusNotFoundException(id));
    }

    @Override
    public StatusResponse findByName(String name) {
        return repository.findByName(name)
                .map(this::toResponseDto)
                .orElseThrow(() -> new StatusNotFoundException("name", name));
    }

    @Override
    public StatusResponse create(StatusRequest dto) {
        validateRequest(dto);
        final Status status = mapper.map(dto, Status.class)
                .setId(new StatusId());
        final Status savedStatus = repository.save(status);
        return mapper.map(savedStatus, StatusResponse.class);
    }

    @Override
    public List<StatusResponse> createAll(List<StatusRequest> dtos) {
        dtos.forEach(this::validateRequest);
        List<Status> statuesList = dtos
                .stream().map(dto -> mapper.map(dto, Status.class))
                .map(status -> status.setId(new StatusId()))
                .toList();

        return repository.saveAll(statuesList)
                .stream().map((this::toResponseDto))
                .toList();
    }

    @Override
    public StatusResponse update(StatusId id, StatusRequest dto) {
        validateRequest(dto);
        Status status = mapper.map(dto, Status.class)
                .setId(id);
        Status savedStatus = repository.save(status);
        return mapper.map(savedStatus, StatusResponse.class);
    }


    @Override
    public void delete(StatusId id) {
        if (repository.existsById(id))
            throw new StatusNotFoundException(id);
        repository.deleteById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

    private StatusResponse toResponseDto(Status status) {
        return mapper.map(status, StatusResponse.class);
    }

    private void validateRequest(StatusRequest dto) {
        Set<ConstraintViolation<StatusRequest>> constraintViolations = validator.validate(dto);

        if (!constraintViolations.isEmpty()) {
            ValidationErrors<StatusRequest> validationErrors = new ValidationErrors<>("validation error in status creation", constraintViolations);
            throw new InvalidRequestException(validationErrors);
        }
    }
}