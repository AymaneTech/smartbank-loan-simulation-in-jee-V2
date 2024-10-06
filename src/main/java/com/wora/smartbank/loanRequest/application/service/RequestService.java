package com.wora.smartbank.loanRequest.application.service;

import com.wora.smartbank.loanRequest.application.dto.RequestRequest;
import com.wora.smartbank.loanRequest.application.dto.RequestResponse;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestId;

import java.util.List;

public interface RequestService {
    List<RequestResponse> findAll();

    RequestResponse findById(RequestId id);

    RequestResponse create(RequestRequest dto);

    RequestResponse update(RequestId id, RequestRequest dto);

    void delete(RequestId id);

    boolean existsById(RequestId id);
}
