package com.example.microserviceaccounts.controller;

import com.example.microserviceaccounts.dto.CustomerDetailsDto;
import com.example.microserviceaccounts.dto.ErrorResponseDto;
import com.example.microserviceaccounts.service.ICustomersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CURD REST APIs for Customers in EasyBank",
        description = "REST APIs in EasyBank to FETCH customer details"
)
@RestController
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@AllArgsConstructor
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final ICustomersService customersService;

    @Operation(
            summary = "Fetch Customer Details REST API",
            description = "REST API to fetch customer details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
            )
            )
    })
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails( @RequestHeader("easybank-correlation-id") String correlationId,
                                                                    @RequestParam
                                                                    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number should be 10 digits")
                                                                    String mobileNumber) {
        logger.debug("fetchCustomerDetails method start");
        CustomerDetailsDto customerDetailsDto =customersService.fetchCustomerDetails(mobileNumber, correlationId);
        logger.debug("fetchCustomerDetails method end");
        return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);
    }
}
