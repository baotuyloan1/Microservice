package com.microservice.cards.functions;

import com.microservice.cards.service.ICardsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class CardsFunctions {

    private static final Logger log = LoggerFactory.getLogger(CardsFunctions.class);

    @Bean
    public Consumer<Long> updateCommunication(ICardsService cardsService){
        return cardId -> {
            log.info("Updating Communication status for the account number: {}", cardId.toString());
            cardsService.updateCommunicationStatus(cardId);
        };
    }
}
