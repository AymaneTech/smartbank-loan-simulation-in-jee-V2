package com.wora.smartbank.loanRequest.domain.entity;

import com.wora.smartbank.common.domain.valueObject.Timestamp;
import com.wora.smartbank.loanRequest.domain.Request;
import com.wora.smartbank.loanRequest.domain.valueObject.RequestStatusId;
import jakarta.persistence.*;

@Entity
@Table(name = "request_status")
public class RequestStatus {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id", nullable = false))
    private RequestStatusId id;

    private String description;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    private Status status;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    private Request request;

    @Embedded
    private Timestamp timestamp;

    protected RequestStatus() {
    }

    public RequestStatus(RequestStatusId id, String description, Status status, Request request) {
        this(description, status, request);
        this.id = id;
    }

    public RequestStatus(String description, Status status, Request request) {
        this.description = description;
        this.status = status;
        this.request = request;
    }

    public RequestStatusId id() {
        return id;
    }

    public RequestStatus setId(RequestStatusId id) {
        this.id = id;
        return this;
    }

    public String description() {
        return description;
    }

    public RequestStatus setDescription(String description) {
        this.description = description;
        return this;
    }

    public Status status() {
        return status;
    }

    public RequestStatus setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Request request() {
        return request;
    }

    public RequestStatus setRequest(Request request) {
        this.request = request;
        return this;
    }

    public Timestamp timestamp() {
        return timestamp;
    }

    public RequestStatus setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @Override
    public String toString() {
        return "RequestStatus{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }
}
