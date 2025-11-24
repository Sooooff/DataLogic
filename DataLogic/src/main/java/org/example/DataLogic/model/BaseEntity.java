package org.example.DataLogic.model;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public abstract class BaseEntity {
    private static final AtomicLong COUNTER = new AtomicLong(1);
    private final long id;
    private final LocalDateTime createdAt;

    public BaseEntity() {
        this.id = COUNTER.getAndIncrement();
        this.createdAt = LocalDateTime.now();
    }

    public long getId() { return id; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}