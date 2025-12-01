package org.example.DataLogic.model;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.ReadOnly;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Long id;

    @Column(name="created_at", updatable = false)
    @ReadOnly
    private LocalDateTime createdAt = LocalDateTime.now();
}