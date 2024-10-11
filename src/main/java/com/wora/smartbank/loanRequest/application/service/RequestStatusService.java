package com.wora.smartbank.loanRequest.application.service;

import com.wora.smartbank.loanRequest.domain.Request;
import com.wora.smartbank.loanRequest.domain.entity.RequestStatus;
import com.wora.smartbank.loanRequest.domain.entity.Status;

public interface RequestStatusService {
    RequestStatus save(Request request, Status status);
}
