package com.baond.message.dto;

public record CardsMsgDto(Long cardId, String mobileNumber, String cardNumber, String cardType, int totalLimit, int amountUsed, int availableAmount) {
}
