package com.wora.smartbank.loanRequest.domain.entity;

import com.wora.smartbank.common.domain.valueObject.Timestamp;
import com.wora.smartbank.loanRequest.domain.valueObject.StatusId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "statuses")
public class Status {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id", nullable = false))
    private StatusId id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "status", cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    private List<RequestStatus> requestStatuses = new ArrayList<>();

    @Embedded
    private Timestamp timestamp;

    public Status() {

    }

    public Status(StatusId id, String name) {
        this.id = id;
        this.name = name;
    }

    public Status(String name) {
        this.name = name;
    }

    public StatusId id() {
        return id;
    }

    public Status setId(StatusId id) {
        this.id = id;
        return this;
    }

    public String name() {
        return name;
    }

    public Status setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
