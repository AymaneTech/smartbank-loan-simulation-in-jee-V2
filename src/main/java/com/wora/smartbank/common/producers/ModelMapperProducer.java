package com.wora.smartbank.common.producers;

import com.wora.smartbank.loanRequest.application.dto.request.RequestRequest;
import com.wora.smartbank.loanRequest.application.dto.response.RequestResponse;
import com.wora.smartbank.loanRequest.application.dto.response.RequestStatusResponse;
import com.wora.smartbank.loanRequest.application.dto.response.StatusResponse;
import com.wora.smartbank.loanRequest.domain.Request;
import com.wora.smartbank.loanRequest.domain.entity.Status;
import com.wora.smartbank.loanRequest.domain.valueObject.Cin;
import com.wora.smartbank.loanRequest.domain.valueObject.CustomerDetails;
import com.wora.smartbank.loanRequest.domain.valueObject.LoanDetails;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

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
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

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
            return mapToRequestResponse(context);
        }, Request.class, RequestResponse.class);

        mapper.addConverter();

        mapper.addConverter(context -> {
            final Status source = context.getSource();
            return new StatusResponse(source.id(), source.name());
        }, Status.class, StatusResponse.class);

    }

    private RequestStatusResponse mapToRequestResponse(Request context){
        
    }
}
