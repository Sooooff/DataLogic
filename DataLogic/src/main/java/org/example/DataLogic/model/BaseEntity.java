package org.example.DataLogic.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Getter
@Setter
public abstract class BaseEntity {
    @Id
    private Long id;
    private static final AtomicLong COUNTER = new AtomicLong(1);
    //private final long id;
    private final LocalDateTime createdAt;

    public BaseEntity() {
        this.id = COUNTER.getAndIncrement();
        this.createdAt = LocalDateTime.now();
    }

    public long getId() { return id; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}