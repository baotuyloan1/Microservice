package com.example.microserviceaccounts.service;

import com.example.microserviceaccounts.dto.CustomerDto;

public interface IAccountService {

    /**
     *
     * @param customerDto - CustomerDto Object
     */
    void createAccount(CustomerDto customerDto);
}
