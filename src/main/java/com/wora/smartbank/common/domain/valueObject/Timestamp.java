package com.wora.smartbank.common.domain.valueObject;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Embeddable
public record Timestamp(
        @CreationTimestamp
        @Temporal(TemporalType.TIMESTAMP)
        LocalDateTime createdAt,


        @UpdateTimestamp
        @Temporal(TemporalType.TIMESTAMP)
        LocalDateTime updatedAt
) {
}
