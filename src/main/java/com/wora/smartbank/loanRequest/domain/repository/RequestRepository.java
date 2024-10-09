package com.wora.smartbank.loanRequest.domain.repository;

import com.wora.smartbank.loanRequest.domain.Request;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestId;
import com.wora.smartbank.orm.api.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, RequestId> {
}
