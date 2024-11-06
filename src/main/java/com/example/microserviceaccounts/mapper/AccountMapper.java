package com.example.microserviceaccounts.mapper;

import com.example.microserviceaccounts.dto.AccountDto;
import com.example.microserviceaccounts.entity.Account;
import com.example.microserviceaccounts.entity.Customer;

public class AccountMapper {

    /**
     * Private constructor to prevent instantiation of this class since it is a utility class
     * and should be used only through static methods.
     */
    private AccountMapper() {
    }

    public static AccountDto mapToAccountDto(Account account, AccountDto accountDto){
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setBranchAddress(account.getBranchAddress());
        return accountDto;
    }

    public static Account mapToAccount(AccountDto accountDto, Account account){
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setAccountType(accountDto.getAccountType());
        account.setBranchAddress(accountDto.getBranchAddress());
        return account;
    }
}
