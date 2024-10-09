package com.wora.smartbank.loanRequest.domain.repository;

import com.wora.smartbank.loanRequest.domain.entity.Status;
import com.wora.smartbank.loanRequest.domain.valueObject.StatusId;
import com.wora.smartbank.orm.api.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, StatusId> {
}