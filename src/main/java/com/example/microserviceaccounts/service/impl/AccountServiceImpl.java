package com.example.microserviceaccounts.service.impl;

import com.example.microserviceaccounts.constants.AccountConstants;
import com.example.microserviceaccounts.dto.CustomerDto;
import com.example.microserviceaccounts.entity.Account;
import com.example.microserviceaccounts.entity.Customer;
import com.example.microserviceaccounts.exception.CustomerAlreadyExistsException;
import com.example.microserviceaccounts.mapper.CustomerMapper;
import com.example.microserviceaccounts.repository.AccountRepository;
import com.example.microserviceaccounts.repository.CustomerRepository;
import com.example.microserviceaccounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private AccountRepository accountsRepository;
    private CustomerRepository customerRepository;


    /**
     * @param customerDto - CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());

        if (optionalCustomer.isPresent()){
            throw  new CustomerAlreadyExistsException("Customer already registered with give mobile number")
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    private Account createNewAccount(Customer customer) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 10000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountConstants.SAVINGS);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);
        return newAccount;
    }
}
