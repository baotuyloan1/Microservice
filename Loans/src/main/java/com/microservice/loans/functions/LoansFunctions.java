package com.microservice.loans.functions;

import com.microservice.loans.service.ILoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class LoansFunctions {

    private static final Logger log = LoggerFactory.getLogger(LoansFunctions.class);

    @Bean
    public Consumer<Long> updateCommunication(ILoanService iLoanService) {
        return loansId -> {
            log.info("Updating Communication status for the account number: {}", loansId.toString());
            iLoanService.updateCommunicationStatus(loansId);
        };
    }

}
