package com.wora.smartbank.loanRequest.domain.repository;

import com.wora.smartbank.common.infrastructure.JpaRepository;
import com.wora.smartbank.loanRequest.domain.Request;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestId;

public interface RequestRepository extends JpaRepository<Request, RequestId> {
}
