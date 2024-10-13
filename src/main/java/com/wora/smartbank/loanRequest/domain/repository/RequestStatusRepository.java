package com.wora.smartbank.loanRequest.domain.repository;

import com.wora.smartbank.loanRequest.domain.entity.RequestStatus;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestId;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestStatusId;
import com.wora.smartbank.orm.api.JpaRepository;

public interface RequestStatusRepository extends JpaRepository<RequestStatus, RequestStatusId> {
    RequestStatus saveWithAssociations(RequestStatus requestStatus);
}
