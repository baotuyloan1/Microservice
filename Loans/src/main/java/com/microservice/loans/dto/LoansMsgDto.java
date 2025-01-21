package com.microservice.loans.dto;

public record LoansMsgDto(Long loanId, String mobileNumber, String loanNumber, String loanType, int totalLoan, int amountPaid, int outstandingAmount) {
}
