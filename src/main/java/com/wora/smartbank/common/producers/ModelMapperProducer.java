package com.wora.smartbank.common.producers;

import com.wora.smartbank.loanRequest.application.dto.RequestRequest;
import com.wora.smartbank.loanRequest.application.dto.RequestResponse;
import com.wora.smartbank.loanRequest.domain.Request;
import com.wora.smartbank.loanRequest.domain.valueObject.Cin;
import com.wora.smartbank.loanRequest.domain.valueObject.CustomerDetails;
import com.wora.smartbank.loanRequest.domain.valueObject.LoanDetails;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.modelmapper.ModelMapper;

public class ModelMapperProducer {

    private ModelMapper mapper;

    @Produces
    @ApplicationScoped
    public ModelMapper createModelMapper() {
        if (mapper == null) {
            mapper = new ModelMapper();
            configureMapper();
        }
        return mapper;
    }

    private void configureMapper() {
        mapper.addConverter(context -> {
            final RequestRequest source = context.getSource();
            return new Request(
                    new LoanDetails(source.project(), source.amount(), source.duration(), source.monthly()),
                    new CustomerDetails(
                            source.title(), source.firstName(), source.lastName(),
                            source.email(), source.phone(), source.profession(),
                            new Cin(source.cin()), source.dateOfBirth(), source.employmentStartDate(),
                            source.monthlyIncome(), source.hasExistingLoans()
                    )
            );
        }, RequestRequest.class, Request.class);

        mapper.addConverter(context -> {
            final Request source = context.getSource();
            return new RequestResponse(
                    source.id(), source.loanDetails().project(), source.loanDetails().amount(), source.loanDetails().duration(), source.loanDetails().monthly(),
                    source.customerDetails().title(), source.customerDetails().firstName(), source.customerDetails().lastName(), source.customerDetails().email(),
                    source.customerDetails().phone(), source.customerDetails().profession(), source.customerDetails().cin().value(), source.customerDetails().dateOfBirth(), source.customerDetails().employmentStartDate(),
                    source.customerDetails().monthlyIncome(), source.customerDetails().hasExistingLoans()
            );
        }, Request.class, RequestResponse.class);

    }
}
