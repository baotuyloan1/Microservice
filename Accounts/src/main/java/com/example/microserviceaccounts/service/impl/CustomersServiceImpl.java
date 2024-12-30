package com.example.microserviceaccounts.service.impl;

import com.example.microserviceaccounts.dto.CustomerDetailsDto;
import com.example.microserviceaccounts.repository.AccountRepository;
import com.example.microserviceaccounts.repository.CustomerRepository;
import com.example.microserviceaccounts.service.ICustomersService;
import com.example.microserviceaccounts.service.client.CardsFeignClient;
import com.example.microserviceaccounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;


    /**
     * @param mobileNumber - Input mobile number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        return null;
    }
}
