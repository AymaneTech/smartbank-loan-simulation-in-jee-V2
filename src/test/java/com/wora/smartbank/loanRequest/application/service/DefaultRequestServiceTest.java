package com.wora.smartbank.loanRequest.application.service;

import com.wora.smartbank.loanRequest.application.dto.request.RequestRequest;
import com.wora.smartbank.loanRequest.application.dto.response.RequestResponse;
import com.wora.smartbank.loanRequest.application.service.impl.DefaultRequestService;
import com.wora.smartbank.loanRequest.domain.Request;
import com.wora.smartbank.loanRequest.domain.exception.RequestNotFoundException;
import com.wora.smartbank.loanRequest.domain.repository.RequestRepository;
import com.wora.smartbank.loanRequest.domain.valueObject.LoanDetails;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestId;
import com.wora.smartbank.loanRequest.infrastructure.seeder.RequestSeeder;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Default Request Service Test")
public class DefaultRequestServiceTest {

    @Mock
    private RequestRepository repository;
    @Mock
    private Validator validator;
    @Mock
    private ModelMapper mapper;
    @Mock
    private LoanCalculationValidationService calculationValidationService;
    @Mock
    private RequestStatusService requestStatusService;

    @InjectMocks
    /*
       @note sut: Stands for System under test
     */
    private DefaultRequestService sut;

    @Nested
    @DisplayName("findAll() method tests")
    class FindAllTests {
        @Test
        @DisplayName("Should return all request responses when requests exist")
        void findAll_ShouldReturnAllRequestResponse_WhenRequestsExist() {
            List<Request> requests = List.of(RequestSeeder.getRequest(), RequestSeeder.getRequest());

            when(repository.findAll()).thenReturn(requests);
            when(mapper.map(any(Request.class), eq(RequestResponse.class)))
                    .thenAnswer(invocation -> {
                        Request req = invocation.getArgument(0);
                        return new RequestResponse(
                                req.id(),
                                req.loanDetails().project(),
                                req.loanDetails().amount(),
                                req.loanDetails().duration(),
                                req.loanDetails().monthly(),
                                req.customerDetails().title(),
                                req.customerDetails().firstName(),
                                req.customerDetails().lastName(),
                                req.customerDetails().email(),
                                req.customerDetails().phone(),
                                req.customerDetails().profession(),
                                req.customerDetails().cin().value(),
                                req.customerDetails().dateOfBirth(),
                                req.customerDetails().employmentStartDate(),
                                req.customerDetails().monthlyIncome(),
                                req.customerDetails().hasExistingLoans());
                    });

            List<RequestResponse> actual = sut.findAll();

            assertEquals(2, actual.size());
            verify(repository).findAll();
            verify(mapper, times(2)).map(any(Request.class), eq(RequestResponse.class));
        }

        @Test
        @DisplayName("Should empty list when no requests found")
        void findAll_ShouldReturnEmptyList_WhenNoRequestsFound() {
            when(repository.findAll()).thenReturn(Collections.emptyList());

            List<RequestResponse> actual = sut.findAll();

            assertTrue(actual.isEmpty());
            verify(repository).findAll();
        }
    }

    @Nested
    @DisplayName("findById() method tests")
    class FindByIdTests {
        @Test
        @DisplayName("Should return request response when given valid ID")
        void findById_ShouldReturnRequestResponse_WhenGivenValidId() {
            Request expected = RequestSeeder.getRequest();
            when(repository.findById(expected.id())).thenReturn(Optional.of(expected));
            when(mapper.map(expected, RequestResponse.class)).thenReturn(new RequestResponse(
                    expected.id(),
                    expected.loanDetails().project(),
                    expected.loanDetails().amount(),
                    expected.loanDetails().duration(),
                    expected.loanDetails().monthly(),
                    expected.customerDetails().title(),
                    expected.customerDetails().firstName(),
                    expected.customerDetails().lastName(),
                    expected.customerDetails().email(),
                    expected.customerDetails().phone(),
                    expected.customerDetails().profession(),
                    expected.customerDetails().cin().value(),
                    expected.customerDetails().dateOfBirth(),
                    expected.customerDetails().employmentStartDate(),
                    expected.customerDetails().monthlyIncome(),
                    expected.customerDetails().hasExistingLoans()));

            RequestResponse actual = sut.findById(expected.id());

            assertNotNull(actual);
            assertEquals(expected.id(), actual.id());
            verify(repository).findById(expected.id());
            verify(mapper).map(expected, RequestResponse.class);
        }

        @Test
        @DisplayName("Should throw RequestNotFoundException when given invalid ID")
        void findById_ShouldThrowRequestNotFoundException_WhenGivenInvalidId() {
            RequestId invalidId = new RequestId();
            when(repository.findById(any(RequestId.class))).thenReturn(Optional.empty());

            assertThrows(RequestNotFoundException.class, () -> sut.findById(invalidId));
            verify(repository).findById(invalidId);
        }
    }

    @Nested
    @DisplayName("create() test methods")
    class CreateTests {
        @Test
        @DisplayName("Should return create request response when given valid data")
        void create_GivenRequest_ShouldReturnRequestResponse() {
            RequestRequest requestDto = RequestSeeder.getRequestRequest();
            Request mappedRequest = RequestSeeder.getRequest();
            Request savedRequest = RequestSeeder.getRequest();
            RequestResponse expectedResponse = new RequestResponse(
                    savedRequest.id(),
                    savedRequest.loanDetails().project(),
                    savedRequest.loanDetails().amount(),
                    savedRequest.loanDetails().duration(),
                    savedRequest.loanDetails().monthly(),
                    savedRequest.customerDetails().title(),
                    savedRequest.customerDetails().firstName(),
                    savedRequest.customerDetails().lastName(),
                    savedRequest.customerDetails().email(),
                    savedRequest.customerDetails().phone(),
                    savedRequest.customerDetails().profession(),
                    savedRequest.customerDetails().cin().value(),
                    savedRequest.customerDetails().dateOfBirth(),
                    savedRequest.customerDetails().employmentStartDate(),
                    savedRequest.customerDetails().monthlyIncome(),
                    savedRequest.customerDetails().hasExistingLoans());

            when(validator.validate(any(RequestRequest.class))).thenReturn(Collections.emptySet());
            when(calculationValidationService.calculate(any(LoanDetails.class)))
                    .thenReturn(mappedRequest.loanDetails());
            when(mapper.map(requestDto, Request.class)).thenReturn(mappedRequest);
            when(repository.save(any(Request.class))).thenReturn(savedRequest);
            when(mapper.map(savedRequest, RequestResponse.class)).thenReturn(expectedResponse);

            RequestResponse actual = sut.create(requestDto);

            assertNotNull(actual);
            assertEquals(expectedResponse.id(), actual.id());
            assertEquals(expectedResponse.project(), actual.project());

            verify(validator).validate(requestDto);
            verify(calculationValidationService).calculate(any(LoanDetails.class));
            verify(mapper).map(requestDto, Request.class);
            verify(repository).save(any(Request.class));
            verify(mapper).map(savedRequest, RequestResponse.class);
        }
    }
}