package com.baond.message.functions;

import com.baond.message.dto.AccountsMsgDto;
import com.baond.message.dto.CardsMsgDto;
import com.baond.message.dto.LoansMsgDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {

    private static final Logger log = LoggerFactory.getLogger(MessageFunctions.class);


    @Bean
    public Function<AccountsMsgDto, AccountsMsgDto> emailAccount(){
        return accountsMsgDto -> {
            log.info("Sending email with the Account details: {}", accountsMsgDto.toString());
            return accountsMsgDto;
        };
    }


    @Bean
    public Function<AccountsMsgDto, Long> smsAccount(){
        return accountsMsgDto -> {
            log.info("Sending sms with the Account details: {}", accountsMsgDto.toString());
            return accountsMsgDto.accountNumber();
        };
    }

    @Bean
    public Function<CardsMsgDto, CardsMsgDto> emailCards(){
        return cardsMsgDto -> {
            log.info("Sending email with the Cards details: {}", cardsMsgDto.toString());
            return cardsMsgDto;
        };
    }

    @Bean
    public Function<CardsMsgDto, Long> smsCards(){
        return cardsMsgDto -> {
            log.info("Sending sms with the Cards details: {}", cardsMsgDto.toString());
            return cardsMsgDto.cardId();
        };
    }

    @Bean
    public Function<LoansMsgDto, LoansMsgDto> emailLoans(){
        return loansMsgDto -> {
            log.info("Sending email with the Loans details: {}", loansMsgDto.toString());
            return loansMsgDto;
        };
    }

    @Bean
    public Function<LoansMsgDto, Long> smsLoans(){
        return loansMsgDto -> {
            log.info("Sending sms with the Loans details: {}", loansMsgDto.toString());
            return loansMsgDto.loanId();
        };
    }

}
