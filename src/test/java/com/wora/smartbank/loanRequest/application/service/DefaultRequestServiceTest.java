package com.wora.smartbank.loanRequest.application.service;

import com.wora.smartbank.loanRequest.application.dto.RequestResponse;
import com.wora.smartbank.loanRequest.application.service.impl.DefaultRequestService;
import com.wora.smartbank.loanRequest.domain.Request;
import com.wora.smartbank.loanRequest.domain.exception.RequestNotFoundException;
import com.wora.smartbank.loanRequest.domain.repository.RequestRepository;
import com.wora.smartbank.loanRequest.domain.valueObject.*;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultRequestServiceTest {

    private static int requestCount = 0;

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
                getRequest(),
                getRequest()
        );

        when(repository.findAll()).thenReturn(requests);

        when(mapper.map(requests.get(0), RequestResponse.class))
                .thenReturn(getMappingResult(requests.get(0)));
        when(mapper.map(requests.get(1), RequestResponse.class))
                .thenReturn(getMappingResult(requests.get(1)));

        List<RequestResponse> actual = sut.findAll();

        assertEquals(2, actual.size());
        assertEquals(requests.get(0).loanDetails().project(), actual.get(0).project());
        assertEquals(requests.get(1).loanDetails().project(), actual.get(1).project());
    }

    @Test
    @DisplayName("findById - given valid id - should return request response ")
    void findById_GivenValidId_ShouldReturnRequestResponse() {
        Request expected = getRequest();

        when(repository.findById(expected.id())).thenReturn(Optional.of(expected));
        when(mapper.map(expected, RequestResponse.class)).thenReturn(getMappingResult(expected));

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


    private Request getRequest() {
        requestCount++;
        return new Request(
                new LoanDetails("project " + requestCount, 15000.00, 36.2, 450.00),
                new CustomerDetails(
                        Title.MR, "firstName " + requestCount, "lastName " + requestCount,
                        "email " + requestCount + "@example.com",
                        "123-456-7890", "Engineer",
                        new Cin("ABC123456789"), LocalDate.of(1990, 5, 20), LocalDate.of(2015, 1, 10),
                        5000.00, false
                )
        ).setId(new RequestId());
    }

    private RequestResponse getMappingResult(Request request) {
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
}