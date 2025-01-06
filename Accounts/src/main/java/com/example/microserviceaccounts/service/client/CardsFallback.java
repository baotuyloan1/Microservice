package com.example.microserviceaccounts.service.client;

import com.example.microserviceaccounts.dto.CardsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallback implements CardsFeignClient {
    /**
     * can choose whatever name inside this FeignClient interface, but please make sure the method signature like input parameters,
     * return parameters along with the method access type should be same as what we have defined inside the actual microservice.
     *
     * @param correlationId
     * @param mobileNumber
     * @return
     */
    @Override
    public ResponseEntity<CardsDto> fetchCardDetailsCustom(String correlationId, String mobileNumber) {
        return null;
    }
}
