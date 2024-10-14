package com.wora.smartbank.common.producers;

import com.wora.smartbank.loanRequest.application.dto.request.RequestRequest;
import com.wora.smartbank.loanRequest.application.dto.response.HistoryItem;
import com.wora.smartbank.loanRequest.application.dto.response.RequestResponse;
import com.wora.smartbank.loanRequest.application.dto.response.RequestWithHistory;
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

import java.util.List;

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

        mapper.addConverter(context -> mapToRequestResponse(context.getSource()),
                Request.class, RequestResponse.class);

        mapper.addConverter(context -> {
            Request source = context.getSource();
            return new RequestWithHistory(mapToRequestResponse(source), historyItems(source));
        }, Request.class, RequestWithHistory.class);

        mapper.addConverter(context -> {
            final Status source = context.getSource();
            return new StatusResponse(source.id(), source.name());
        }, Status.class, StatusResponse.class);

    }

    private List<HistoryItem> historyItems(Request source) {
        return source.requestStatuses()
                .stream()
                .map(requestStatus -> new HistoryItem(new StatusResponse(
                        requestStatus.status()),
                        requestStatus.description(),
                        requestStatus.timestamp().createdAt()
                ))
                .toList();
    }

    private RequestResponse mapToRequestResponse(Request source) {
        return new RequestResponse(
                source.id(), source.loanDetails().project(), source.loanDetails().amount(), source.loanDetails().duration(), source.loanDetails().monthly(),
                source.customerDetails().title(), source.customerDetails().firstName(), source.customerDetails().lastName(), source.customerDetails().email(),
                source.customerDetails().phone(), source.customerDetails().profession(), source.customerDetails().cin().value(), source.customerDetails().dateOfBirth(), source.customerDetails().employmentStartDate(),
                source.customerDetails().monthlyIncome(), source.customerDetails().hasExistingLoans()
        );

    }
}
