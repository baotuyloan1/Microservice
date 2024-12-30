package com.example.microserviceaccounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "CustomerDetails",
        description = "Schema to hold Customer, Account, Cards and Loans information"
)
public class CustomerDetailsDto {

    @Schema(
            description = "Name of the Customer",
            example = "Bao Nguyen"
    )
    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    private String name;

    @Schema(
            description = "Email address of the Customer",
            example = "nguyenducbaokey@gmail.com"
    )
    @NotEmpty(message = "Email address cannot be null or empty")
    @Email(message = "Email address should be valid")
    private String email;

    @Schema(
            description = "Mobile Number of the customer",
            example = "0788049042"
    )
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number should be 10 digits")
    private String mobileNumber;


    @Schema(
            description = "Account details of the customer"
    )
    private AccountDto accountsDto;

    @Schema(
            description = "Loans details of the customer"
    )
    private AccountDto loansDto;


    @Schema(
            description = "Cards details of the customer"
    )
    private AccountDto cardsDto;
}
