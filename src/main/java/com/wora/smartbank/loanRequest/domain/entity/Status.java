package com.wora.smartbank.loanRequest.domain.entity;

import com.wora.smartbank.common.domain.valueObject.Timestamp;
import com.wora.smartbank.loanRequest.domain.valueObject.StatusId;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "statuses")
public class Status {

    @EmbeddedId
    private StatusId id;

    private String description;

    @OneToMany(mappedBy = "status", cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    private List<RequestStatus> requestStatuses;

    @Embedded
    private Timestamp timestamp;

    protected Status() {

    }

    public Status(StatusId id, String description) {
        this.id = id;
        this.description = description;
    }

    public Status(String description) {
        this.description = description;
    }

    public StatusId id() {
        return id;
    }

    public Status setId(StatusId id) {
        this.id = id;
        return this;
    }

    public String description() {
        return description;
    }

    public Status setDescription(String description) {
        this.description = description;
        return this;
    }

    public Timestamp timestamp() {
        return timestamp;
    }

    public Status setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public List<RequestStatus> requestStatuses() {
        return requestStatuses;
    }

    public Status setRequestStatuses(List<RequestStatus> requestStatuses) {
        this.requestStatuses = requestStatuses;
        return this;
    }
}
