package com.example.microserviceaccounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
@Data
public class AccountDto {

    @Schema(
            description = "Account Number of EasyBank Account",
            example = "5262638432"
    )
    @NotEmpty(message = "AccountNumber can not be a null or empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "AccountNumber can only contain numbers")
    private Long accountNumber;

    @NotEmpty(message = "AccountType can not be a null or empty")
    @Schema(
            description = "Account type of EasyBank Account",
            example = "SAVINGS"
    )
    private String accountType;

    @Schema(
            description = "Easy Bank branch address",
            example = "123 DaNang city"
    )
    @NotEmpty(message = "BranchAddress can not be a null or empty")
    private String branchAddress;
}
