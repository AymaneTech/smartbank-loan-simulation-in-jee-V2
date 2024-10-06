package com.wora.smartbank.loanRequest.domain;

import com.wora.smartbank.common.domain.valueObject.Timestamp;
import com.wora.smartbank.loanRequest.domain.valueObject.CustomerDetails;
import com.wora.smartbank.loanRequest.domain.valueObject.LoanDetails;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestId;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "requests")
public class Request implements Serializable {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private RequestId id;

    @Embedded
    private LoanDetails loanDetails;

    @Embedded
    private CustomerDetails customerDetails;

    @Embedded
    private Timestamp timestamp;

    protected Request() {
    }

    public Request(LoanDetails loanDetails, CustomerDetails customerDetails) {
        this.loanDetails = loanDetails;
        this.customerDetails = customerDetails;
    }

    public RequestId id() {
        return id;
    }

    public Request setId(RequestId id) {
        this.id = id;
        return this;
    }

    public LoanDetails loanDetails() {
        return loanDetails;
    }

    public Request setLoanDetails(LoanDetails loanDetails) {
        this.loanDetails = loanDetails;
        return this;
    }

    public CustomerDetails customerDetails() {
        return customerDetails;
    }

    public Request setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
        return this;
    }

    public Timestamp timestamp() {
        return timestamp;
    }

    public Request setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", loanDetails=" + loanDetails +
                ", customerDetails=" + customerDetails +
                '}';
    }
}