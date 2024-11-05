package com.example.microserviceaccounts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Setter @Getter @ToString @AllArgsConstructor @NoArgsConstructor
public class Account {

    private Long customerId;

    @Id
    private Long accountNumber;

    private String accountType;

    private String branchAddress;
}
