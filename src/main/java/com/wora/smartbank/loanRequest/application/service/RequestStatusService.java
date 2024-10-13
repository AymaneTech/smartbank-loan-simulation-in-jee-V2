package com.wora.smartbank.loanRequest.application.service;

import com.wora.smartbank.loanRequest.application.dto.RequestStatusRequest;
import com.wora.smartbank.loanRequest.domain.entity.RequestStatus;

public interface RequestStatusService {
    RequestStatus save(RequestStatusRequest dto);
}
