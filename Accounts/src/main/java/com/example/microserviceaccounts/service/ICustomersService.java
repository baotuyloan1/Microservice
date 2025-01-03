package com.example.microserviceaccounts.service;

import com.example.microserviceaccounts.dto.CustomerDetailsDto;

public interface ICustomersService {

    /**
     *
     * @param mobileNumber - Input mobile number
     * @return Customer Details based on a given mobileNumber
     */
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId);
}
