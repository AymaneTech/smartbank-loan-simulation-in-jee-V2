package com.wora.smartbank.loanRequest.application.service;

import com.wora.smartbank.loanRequest.application.dto.RequestStatusDto;
import com.wora.smartbank.loanRequest.domain.entity.RequestStatus;

public interface RequestStatusService {
    RequestStatus create(RequestStatusDto dto);
}
