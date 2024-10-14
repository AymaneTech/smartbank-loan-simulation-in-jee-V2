package com.wora.smartbank.loanRequest.application.service;

import com.wora.smartbank.loanRequest.application.dto.request.RequestRequest;
import com.wora.smartbank.loanRequest.application.dto.response.RequestResponse;
import com.wora.smartbank.loanRequest.application.dto.response.RequestWithHistory;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestId;

import java.util.List;

public interface RequestService {
    List<RequestResponse> findAll();

    RequestResponse findById(RequestId id);

    RequestResponse create(RequestRequest dto);

    RequestResponse update(RequestId id, RequestRequest dto);

    void delete(RequestId id);

    boolean existsById(RequestId id);

    RequestWithHistory findByIdWithHistory(RequestId id);
}
