package com.wora.smartbank.loanRequest.application.service.impl;

import com.wora.smartbank.common.domain.exception.InvalidRequestException;
import com.wora.smartbank.common.domain.valueObject.ValidationErrors;
import com.wora.smartbank.loanRequest.application.dto.RequestRequest;
import com.wora.smartbank.loanRequest.application.dto.RequestResponse;
import com.wora.smartbank.loanRequest.application.service.LoanCalculationValidationService;
import com.wora.smartbank.loanRequest.application.service.RequestService;
import com.wora.smartbank.loanRequest.domain.Request;
import com.wora.smartbank.loanRequest.domain.exception.RequestNotFoundException;
import com.wora.smartbank.loanRequest.domain.repository.RequestRepository;
import com.wora.smartbank.loanRequest.domain.valueObject.LoanDetails;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@ApplicationScoped
public class DefaultRequestService implements RequestService {

    private final RequestRepository repository;
    private final ModelMapper mapper;
    private final Validator validator;
    private final LoanCalculationValidationService calculationValidationService;

    @Inject
    public DefaultRequestService(RequestRepository repository, ModelMapper mapper, Validator validator, LoanCalculationValidationService calculationValidationService) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
        this.calculationValidationService = calculationValidationService;
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
        validateRequest(dto);

        final Request savedRequest = repository.save(mapper.map(dto, Request.class));
        return mapper.map(savedRequest, RequestResponse.class);
    }

    @Override
    public RequestResponse update(RequestId id, RequestRequest dto) {
        validateRequest(dto);
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

    private void validateRequest(RequestRequest dto) {
        Set<ConstraintViolation<RequestRequest>> constraintViolations = validator.validate(dto);

        if (!constraintViolations.isEmpty()) {
            ValidationErrors<RequestRequest> validationErrors = new ValidationErrors<>("validation error in request creation", constraintViolations);
            throw new InvalidRequestException(validationErrors);
        }

        if (!calculationValidationService.validate(new LoanDetails(dto.project(), dto.amount(), dto.duration(), dto.monthly()))) {
            ValidationErrors<RequestRequest> validationErrors = new ValidationErrors<>("loan calculation errors", Map.of("calculation", "loan calculation error"));
            throw new InvalidRequestException(validationErrors);
        }

    }
}
