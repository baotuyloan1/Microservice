package com.microservice.cards.service.impl;

import com.microservice.cards.constants.CardsConstants;
import com.microservice.cards.dto.CardsDto;
import com.microservice.cards.dto.CardsMsgDto;
import com.microservice.cards.entity.Cards;
import com.microservice.cards.exception.CardAlreadyExistsException;
import com.microservice.cards.exception.ResourceNotFoundException;
import com.microservice.cards.mapper.CardsMapper;
import com.microservice.cards.repository.CardsRepository;
import com.microservice.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    private CardsRepository cardsRepository;
    private StreamBridge streamBridge;

    private static final Logger log = LoggerFactory.getLogger(CardsServiceImpl.class);

    /**
     * @param mobileNumber - Mobile Number of the Customer
     */
    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> optionalCards= cardsRepository.findByMobileNumber(mobileNumber);
        if(optionalCards.isPresent()){
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber "+mobileNumber);
        }
        Cards cards = cardsRepository.save(createNewCard(mobileNumber));
        sendCommunication(cards);
    }

    private void sendCommunication(Cards cards){
        CardsMsgDto cardsMsgDto = new CardsMsgDto(cards.getCardId(), cards.getMobileNumber(), cards.getCardNumber(), cards.getCardType(), cards.getTotalLimit(), cards.getAmountUsed(), cards.getAvailableAmount());
        log.info("Sending Communication request for the details: {}", cardsMsgDto);
        var result = streamBridge.send("sendCommunication-out-0", cardsMsgDto);
        log.info("Is the Communication request successfully triggered?: {}", result);
    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new card details
     */
    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }

    /**
     * @param mobileNumber - Input mobile Number
     * @return Card Details based on a given mobileNumber
     */
    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        return CardsMapper.mapToCardsDto(cards, new CardsDto());
    }
    /**
     * @param cardsDto - CardsDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    @Override
    public boolean updateCard(CardsDto cardsDto) {
        Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "CardNumber", cardsDto.getCardNumber()));
        CardsMapper.mapToCards(cardsDto, cards);
        cardsRepository.save(cards);
        return  true;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of card details is successful or not
     */
    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        cardsRepository.deleteById(cards.getCardId());
        return true;
    }

    /**
     * @param cardId - Long
     * @return boolean indicating if the update of communication status is successful or not
     */
    @Override
    public boolean updateCommunicationStatus(Long cardId) {
        boolean isUpdated = false;
        if (cardId != null){
            Cards cards = cardsRepository.findById(cardId).orElseThrow(
                    () -> new ResourceNotFoundException("Cards", "cardId", String.valueOf(cardId))
            );
            cards.setCommunicationSw(true);
            cardsRepository.save(cards);
            isUpdated = true;
        }
        return isUpdated;
    }
}
