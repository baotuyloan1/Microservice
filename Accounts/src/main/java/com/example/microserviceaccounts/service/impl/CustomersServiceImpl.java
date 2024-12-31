package com.example.microserviceaccounts.service.impl;

import com.example.microserviceaccounts.dto.AccountDto;
import com.example.microserviceaccounts.dto.CardsDto;
import com.example.microserviceaccounts.dto.CustomerDetailsDto;
import com.example.microserviceaccounts.dto.LoansDto;
import com.example.microserviceaccounts.entity.Account;
import com.example.microserviceaccounts.entity.Customer;
import com.example.microserviceaccounts.exception.ResourceNotFoundException;
import com.example.microserviceaccounts.mapper.AccountMapper;
import com.example.microserviceaccounts.mapper.CustomerMapper;
import com.example.microserviceaccounts.repository.AccountRepository;
import com.example.microserviceaccounts.repository.CustomerRepository;
import com.example.microserviceaccounts.service.ICustomersService;
import com.example.microserviceaccounts.service.client.CardsFeignClient;
import com.example.microserviceaccounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final CardsFeignClient cardsFeignClient;
    private final LoansFeignClient loansFeignClient;


    /**
     * @param mobileNumber - Input mobile number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer","mobileNumber",mobileNumber)
        );
        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()-> new ResourceNotFoundException("Account","customerId",String.valueOf(customer.getCustomerId()))
        );
        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountMapper.mapToAccountDto(account, new AccountDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity =  loansFeignClient.fetchLoanDetails(mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity =  cardsFeignClient.fetchCardDetailsCustom(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}
