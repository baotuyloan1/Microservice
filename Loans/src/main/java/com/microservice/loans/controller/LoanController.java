package com.microservice.loans.controller;

import com.microservice.loans.constants.LoanConstants;
import com.microservice.loans.dto.ErrorResponseDto;
import com.microservice.loans.dto.LoansDto;
import com.microservice.loans.dto.ResponseDto;
import com.microservice.loans.service.ILoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "CRUD REST APIs for Loans in EasyBank", description = "CURD REST APIs in EasyBank to CREATE, FETCH, UPDATE, DELETE Loans details")
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@RestController
@Validated
public class LoanController {

    private final ILoanService iLoanService;

    @Operation(
            summary = "Create Loan REST API",
            description = "REST API to create new Loan inside EasyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = LoanConstants.STATUS_201,
                    description = LoanConstants.MESSAGE_201
            ),
            @ApiResponse(
                    responseCode = LoanConstants.STATUS_500,
                    description = LoanConstants.MESSAGE_500,
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(
            @RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be 10 digits")
            String mobileNumber) {
        iLoanService.createLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(LoanConstants.STATUS_201, LoanConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Loan Details REST API",
            description = "REST API to fetch Loan details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = LoanConstants.STATUS_200,
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = LoanConstants.STATUS_500,
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/fetch")
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestParam
                                                     @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be 10 digits")
                                                     String mobileNumber) {
        LoansDto loansDto = iLoanService.fetchLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loansDto);

    }


    @Operation(
            summary = "Update Loan Details REST API",
            description = "REST API to update Loan details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = LoanConstants.STATUS_200,
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = LoanConstants.STATUS_417,
                    description = "HTTP Status Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = LoanConstants.STATUS_500,
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> uploadLoanDetails(@Valid @RequestBody LoansDto loansDto) {
        boolean isLoanUpdated = iLoanService.updateLoan(loansDto);
        return ResponseEntity.status(isLoanUpdated ? HttpStatus.OK : HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(isLoanUpdated ? LoanConstants.STATUS_200 : LoanConstants.STATUS_417,
                        isLoanUpdated ? LoanConstants.MESSAGE_200 : LoanConstants.MESSAGE_417_UPDATE));
    }



    @Operation(
            summary = "Delete Loan Details REST API",
            description = "REST API to delete Loan details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteLoanDetails(@RequestParam
                                                         @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                         String mobileNumber) {
        boolean isDeleted = iLoanService.deleteLoan(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstants.STATUS_417, LoanConstants.MESSAGE_417_DELETE));
        }
    }

}
