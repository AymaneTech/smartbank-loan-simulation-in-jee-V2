package com.wora.smartbank.loanRequest.application.service.impl;

import com.wora.smartbank.common.domain.exception.InvalidRequestException;
import com.wora.smartbank.common.domain.valueObject.ValidationErrors;
import com.wora.smartbank.loanRequest.application.dto.RequestRequest;
import com.wora.smartbank.loanRequest.application.dto.RequestResponse;
import com.wora.smartbank.loanRequest.application.service.LoanCalculationValidationService;
import com.wora.smartbank.loanRequest.application.service.RequestService;
import com.wora.smartbank.loanRequest.application.service.StatusService;
import com.wora.smartbank.loanRequest.domain.Request;
import com.wora.smartbank.loanRequest.domain.entity.Status;
import com.wora.smartbank.loanRequest.domain.exception.RequestNotFoundException;
import com.wora.smartbank.loanRequest.domain.exception.StatusNotFoundException;
import com.wora.smartbank.loanRequest.domain.valueObject.LoanDetails;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestId;
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
public class DefaultRequestService implements RequestService {

    private final JpaRepository<Request, RequestId> repository;
    private final JpaRepository<Status, StatusId> statusRepository;
    private final ModelMapper mapper;
    private final Validator validator;
    private final LoanCalculationValidationService calculationValidationService;

    @Inject
    public DefaultRequestService(@JPA JpaRepository<Request, RequestId> repository, JpaRepository<Status, StatusId> statusRepository, ModelMapper mapper, Validator validator, StatusService statusService, LoanCalculationValidationService calculationValidationService) {
        this.repository = repository;
        this.statusRepository = statusRepository;
        this.mapper = mapper;
        this.validator = validator;
        this.calculationValidationService = calculationValidationService;
    }

    @Override
    public List<RequestResponse> findAll() {
        return repository.findAll()
                .stream()
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
        Status status = statusRepository.findByColumn("name", "PENDING")
                .orElseThrow(() -> new StatusNotFoundException("name", "PENDING"));

        final Request request = validateAndCalculation(dto)
                .setId(new RequestId())
                .addStatus(status);
        final Request savedRequest = repository.save(request);
        return mapper.map(savedRequest, RequestResponse.class);
    }

    @Override
    public RequestResponse update(RequestId id, RequestRequest dto) {
        Request request = validateAndCalculation(dto)
                .setId(id);
        final Request savedRequest = repository.update(request);
        return mapper.map(savedRequest, RequestResponse.class);
    }

    @Override
    public void delete(RequestId id) {
        if (!repository.existsById(id))
            throw new RequestNotFoundException(id);
        repository.deleteById(id);
    }

    @Override
    public boolean existsById(RequestId id) {
        return repository.existsById(id);
    }

    private Request validateAndCalculation(RequestRequest dto) {
        validateRequest(dto);

        final LoanDetails loanDetails = calculationValidationService.calculate(new LoanDetails(dto.project(), dto.amount(), dto.duration(), dto.monthly()));
        final Request mappedRequest = mapper.map(dto, Request.class);
        mappedRequest.setLoanDetails(loanDetails);
        return mappedRequest;
    }

    private void validateRequest(RequestRequest dto) {
        Set<ConstraintViolation<RequestRequest>> constraintViolations = validator.validate(dto);

        if (!constraintViolations.isEmpty()) {
            ValidationErrors<RequestRequest> validationErrors = new ValidationErrors<>("validation error in request creation", constraintViolations);
            throw new InvalidRequestException(validationErrors);
        }
    }
}
