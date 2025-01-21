package com.baond.message.dto;

public record LoansMsgDto(Long loanId, String mobileNumber, String loanNumber, String loanType, int totalLoan, int amountPaid, int outstandingAmount) {
}
