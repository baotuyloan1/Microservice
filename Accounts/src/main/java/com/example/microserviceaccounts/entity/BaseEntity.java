package com.example.microserviceaccounts.entity;

import com.example.microserviceaccounts.audit.AuditAwareImpl;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * The @EntityListeners annotation is used to specify the callback listener classes
 * for an entity or mapped superclass. These listeners can react to lifecycle events
 * such as pre-persist, post-persist, pre-remove, post-remove, pre-update, post-update,
 * and post-load.
 *
 * In this case, AuditingEntityListener.class is specified as the entity listener, which means
 * it will handle the auditing operations such as setting the createdBy, createdAt,
 * updatedBy, and updatedAt fields whenever the corresponding lifecycle events occur.
 */
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter @Setter @ToString
public class BaseEntity {

    @CreatedDate
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(insertable = false)
    private String updatedBy;


}
