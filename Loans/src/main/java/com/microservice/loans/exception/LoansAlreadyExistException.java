package com.microservice.loans.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a loan already exists for a given mobile number.
 * <p>
 * The @ResponseStatus annotation is only used when the exception is thrown and not caught by any exception handler.
 * If an exception handler catches the exception and returns a response, the @ResponseStatus annotation is ignored.
 * <p>
 * In this case, since the handleLoansAlreadyExistException method in GlobalExceptionHandler is catching
 * LoansAlreadyExistException and returning a response with HttpStatus.BAD_REQUEST, the @ResponseStatus annotation
 * on the LoansAlreadyExistException class is not necessary and can be removed.
 */
//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LoansAlreadyExistException extends RuntimeException {
    public LoansAlreadyExistException(String message) {
        super(message);
    }
}
