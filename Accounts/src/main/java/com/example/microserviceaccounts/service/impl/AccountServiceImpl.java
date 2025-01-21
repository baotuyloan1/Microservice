package com.example.microserviceaccounts.service.impl;

import com.example.microserviceaccounts.constants.AccountConstants;
import com.example.microserviceaccounts.dto.AccountDto;
import com.example.microserviceaccounts.dto.AccountsMsgDto;
import com.example.microserviceaccounts.dto.CustomerDto;
import com.example.microserviceaccounts.entity.Account;
import com.example.microserviceaccounts.entity.Customer;
import com.example.microserviceaccounts.exception.CustomerAlreadyExistsException;
import com.example.microserviceaccounts.exception.ResourceNotFoundException;
import com.example.microserviceaccounts.mapper.AccountMapper;
import com.example.microserviceaccounts.mapper.CustomerMapper;
import com.example.microserviceaccounts.repository.AccountRepository;
import com.example.microserviceaccounts.repository.CustomerRepository;
import com.example.microserviceaccounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    private AccountRepository accountsRepository;
    private CustomerRepository customerRepository;

//    used for binding with rabbitmq
    private final StreamBridge streamBridge;

    /**
     * @param customerDto - CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());

        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with give mobile number " + customerDto.getMobileNumber());
        }

        Customer savedCustomer = customerRepository.save(customer);
        Account savedAccounts = accountsRepository.save(createNewAccount(savedCustomer));
        sendCommunication(savedAccounts, savedCustomer);
    }


    private void sendCommunication(Account account, Customer customer){
        var accountMsgDto = new AccountsMsgDto(account.getAccountNumber(), customer.getName(), customer.getEmail(), customer.getMobileNumber() );
        log.info("Sending Communication request for the details: {}", accountMsgDto);
        var result = streamBridge.send("sendCommunication-out-0", accountMsgDto);
        log.info("Is the Communication request successfully triggered?: {}", result);
    }


    private Account createNewAccount(Customer customer) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountConstants.SAVINGS);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);

        return newAccount;
    }

    /**
     * @param mobileNumber - Input mobile number
     * @return Accounts Details based on a given mobileNumber
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Account account = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountDto(AccountMapper.mapToAccountDto(account, new AccountDto()));
        return customerDto;
    }

    /**
     * @param customerDto - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountDto accountDto = customerDto.getAccountDto();
        if (accountDto != null) {
            Account account = accountsRepository.findById(accountDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "accountNumber", String.valueOf(accountDto.getAccountNumber()))
            );
            AccountMapper.mapToAccount(accountDto, account);
            account = accountsRepository.save(account);

            Long customerId = account.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "customerId", String.valueOf(customerId))
            );

            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the deletion of Account is successful or not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    /**
     * @param accountNumber - Long
     * @return boolean indicating if the update of communication status is successful or not
     */
    @Override
    public boolean updateCommunicationStatus(Long accountNumber) {
        boolean isUpdated = false;
        if (accountNumber != null){
            Account account = accountsRepository.findById(accountNumber).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "accountNumber", String.valueOf(accountNumber))
            );
            account.setCommunicationSw(true);
            accountsRepository.save(account);
            isUpdated = true;
        }
        return isUpdated;
    }


}
