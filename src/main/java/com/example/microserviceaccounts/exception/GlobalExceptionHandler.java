package com.example.microserviceaccounts.exception;

import com.example.microserviceaccounts.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to handle all the exceptions thrown from this project.
 * All the exceptions will be caught here and converted to a custom exception.
 * This is done to avoid duplication of try catch blocks in every method.
 */


/**
 * GlobalExceptionHandler is a centralized exception handler that extends ResponseEntityExceptionHandler.
 * It is responsible for handling exceptions globally across the application and converting them into meaningful
 * HTTP responses with custom error details.
 *
 * By extending ResponseEntityExceptionHandler, this class can leverage the built-in exception handling
 * capabilities provided by Spring, such as handling specific exceptions like MethodArgumentNotValidException
 * and HttpMessageNotReadableException. This ensures a consistent and standardized response format for
 * client-side errors.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    /**
     * This method is overridden from ResponseEntityExceptionHandler to handle MethodArgumentNotValidException.
     * This exception is thrown when there is an error in validating the input argument of any method.
     *
     * @param ex the MethodArgumentNotValidException that is thrown
     * @param headers the HttpHeaders that is sent with the response
     * @param status the HttpStatusCode that is sent with the response
     * @param request the current WebRequest
     * @return a ResponseEntity with the custom error response
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();

        // this will give me all validation errors failed in the input data that I received
        List<ObjectError> validationErrorsList = ex.getBindingResult().getAllErrors();
        validationErrorsList.forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            // this message is from  message when setting validation error on Dto class
            String errorMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistException(CustomerAlreadyExistsException exception, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles all generic exceptions that are not specifically caught by other exception handlers.
     *
     * This method is invoked by the Spring Boot application when an exception of type `Exception`
     * is thrown and not caught by any specific handler. It provides a generic response for all
     * uncaught exceptions. Although `ResourceNotFoundException` and `CustomerAlreadyExistsException`
     * are also types of `Exception`, they have their own specific handlers because the
     * Spring Boot application looks for the closest matching handler method for the exact exception type.
     *
     * @param exception  the exception that was thrown
     * @param webRequest the current web request
     * @return a ResponseEntity containing an ErrorResponseDto with details of the error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
