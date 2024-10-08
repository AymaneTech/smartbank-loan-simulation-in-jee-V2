package com.wora.smartbank.loanRequest.application.service;

import com.wora.smartbank.loanRequest.application.dto.RequestRequest;
import com.wora.smartbank.loanRequest.application.dto.RequestResponse;
import com.wora.smartbank.loanRequest.application.service.impl.DefaultRequestService;
import com.wora.smartbank.loanRequest.domain.Request;
import com.wora.smartbank.loanRequest.domain.exception.RequestNotFoundException;
import com.wora.smartbank.loanRequest.domain.repository.RequestRepository;
import com.wora.smartbank.loanRequest.domain.valueObject.Cin;
import com.wora.smartbank.loanRequest.domain.valueObject.CustomerDetails;
import com.wora.smartbank.loanRequest.domain.valueObject.LoanDetails;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestId;
import com.wora.smartbank.loanRequest.infrastructure.seeder.RequestSeeder;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultRequestServiceTest {

    @Mock
    private RequestRepository repository;

    @Mock
    private Validator validator;

    @Mock
    private ModelMapper mapper;

    @Mock
    private LoanCalculationValidationService calculationValidationService;

    @InjectMocks
    private DefaultRequestService sut;

    @Test
    @DisplayName("findAll - should return all request responses")
    void findAll_ShouldReturnAllRequests() {
        List<Request> requests = List.of(
                RequestSeeder.getRequest(),
                RequestSeeder.getRequest()
        );

        when(repository.findAll()).thenReturn(requests);

        when(mapper.map(requests.get(0), RequestResponse.class))
                .thenReturn(mapEntityToResponseDto(requests.get(0)));
        when(mapper.map(requests.get(1), RequestResponse.class))
                .thenReturn(mapEntityToResponseDto(requests.get(1)));

        List<RequestResponse> actual = sut.findAll();

        assertEquals(2, actual.size());
        assertEquals(requests.get(0).loanDetails().project(), actual.get(0).project());
        assertEquals(requests.get(1).loanDetails().project(), actual.get(1).project());
    }

    @Test
    @DisplayName("findById - given valid id - should return request response ")
    void findById_GivenValidId_ShouldReturnRequestResponse() {
        Request expected = RequestSeeder.getRequest();

        when(repository.findById(expected.id())).thenReturn(Optional.of(expected));
        when(mapper.map(expected, RequestResponse.class)).thenReturn(mapEntityToResponseDto(expected));

        RequestResponse actual = sut.findById(expected.id());

        assertEquals(expected.id(), actual.id());
        assertEquals(expected.loanDetails().project(), actual.project());
    }

    @Test
    @DisplayName("findById - given invalid id - should throw requestNotFoundException")
    void findById_GivenInvalidId_ShouldThrowRequestNoFoundException() {
        RequestId invalidId = new RequestId();
        when(repository.findById(any(RequestId.class))).thenReturn(Optional.empty());

        assertThrows(RequestNotFoundException.class, () -> sut.findById(invalidId));
    }

    @Test
    @DisplayName("create - given valid data - return created request response")
    void create_GivenRequest_ShouldReturnRequestResponse() {
        RequestRequest requestDto = RequestSeeder.getRequestRequest();

        when(validator.validate(any(RequestRequest.class)))
                .thenReturn(Collections.emptySet());

        when(calculationValidationService.calculate(any(LoanDetails.class)))
                .thenReturn(new LoanDetails(requestDto.project(), requestDto.amount(), requestDto.duration(), requestDto.monthly()));

        Request request = mapDtoToEntity(requestDto);
        when(repository.save(any(Request.class)))
                .thenReturn(request);

        when(mapper.map(any(Request.class), eq(RequestResponse.class)))
                .thenReturn(mapEntityToResponseDto(request));

        RequestResponse actual = sut.create(requestDto);

        assertEquals(requestDto.project(), actual.project());
        assertNotNull(request);
    }

    private RequestResponse mapEntityToResponseDto(Request request) {
        return new RequestResponse(
                request.id(),
                request.loanDetails().project(),
                request.loanDetails().amount(),
                request.loanDetails().duration(),
                request.loanDetails().monthly(),
                request.customerDetails().title(),
                request.customerDetails().firstName(),
                request.customerDetails().lastName(),
                request.customerDetails().email(),
                request.customerDetails().phone(),
                request.customerDetails().profession(),
                request.customerDetails().cin().value(),
                request.customerDetails().dateOfBirth(),
                request.customerDetails().employmentStartDate(),
                request.customerDetails().monthlyIncome(),
                request.customerDetails().hasExistingLoans()
        );
    }

    private Request mapDtoToEntity(RequestRequest request) {
        return new Request(new LoanDetails(request.project(), request.amount(), request.duration(), request.monthly()),
                new CustomerDetails(request.title(), request.firstName(), request.lastName(), request.email(),
                        request.phone(), request.profession(), new Cin(request.cin()), request.dateOfBirth(), request.employmentStartDate(),
                        request.monthlyIncome(), request.hasExistingLoans()));
    }
}