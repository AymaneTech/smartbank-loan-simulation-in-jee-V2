package com.wora.smartbank.loanRequest.application.service.impl;

import com.wora.smartbank.common.domain.exception.InvalidRequestException;
import com.wora.smartbank.common.domain.valueObject.ValidationErrors;
import com.wora.smartbank.loanRequest.application.dto.StatusRequest;
import com.wora.smartbank.loanRequest.application.dto.StatusResponse;
import com.wora.smartbank.loanRequest.application.service.StatusService;
import com.wora.smartbank.loanRequest.domain.entity.Status;
import com.wora.smartbank.loanRequest.domain.exception.StatusNotFoundException;
import com.wora.smartbank.loanRequest.domain.valueObject.StatusId;
import com.wora.smartbank.orm.api.JpaRepository;
import com.wora.smartbank.orm.api.annotation.JPA;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Set;

@ApplicationScoped
public class DefaultStatusService implements StatusService {

    private final JpaRepository<Status, StatusId> repository;
    private final ModelMapper mapper;
    private final Validator validator;

    @Inject
    public DefaultStatusService(@JPA JpaRepository<Status, StatusId> repository, ModelMapper mapper, Validator validator) {
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
    public StatusResponse create(StatusRequest dto) {
        validateRequest(dto);
        final Status status = mapper.map(dto, Status.class)
                .setId(new StatusId());
        final Status savedStatus = repository.save(status);
        return mapper.map(savedStatus, StatusResponse.class);
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