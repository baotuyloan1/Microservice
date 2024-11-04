package com.example.microserviceaccounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@MappedSuperclass
@Getter @Setter @ToString
public class BaseEntity {

    @Column(updatable = false)
    private LocalDateTime createAt;
    @Column(insertable = false)
    private LocalDateTime updateAt;
    @Column(updatable = false)
    private String createdBy;
    @Column(insertable = false)
    private String updatedBy;


}
