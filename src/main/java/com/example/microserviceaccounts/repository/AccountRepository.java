package com.example.microserviceaccounts.repository;

import com.example.microserviceaccounts.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
