package com.wora.smartbank.loanRequest.application.service;

import com.wora.smartbank.loanRequest.application.dto.StatusRequest;
import com.wora.smartbank.loanRequest.application.dto.StatusResponse;
import com.wora.smartbank.loanRequest.domain.valueObject.StatusId;

import java.util.List;

public interface StatusService {

    StatusResponse create(StatusRequest dto);

    List<StatusResponse> createAll(List<StatusRequest> dto);

    StatusResponse update(StatusId id, StatusRequest dto);

    List<StatusResponse> findAll();

    StatusResponse findById(StatusId statusId);

    StatusResponse findByName(String name);

    void delete(StatusId id);

    long count();
}
