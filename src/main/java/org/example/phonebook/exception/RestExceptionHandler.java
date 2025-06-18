package org.example.phonebook.exception;

import org.example.phonebook.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Global exception handler for REST controllers.
 *
 * <p>Handles application-specific exceptions and validation errors,
 * returning appropriate HTTP status codes and error details in the response body.
 */
@ControllerAdvice
public class RestExceptionHandler {

    /**
     * Handles ContactNotFoundException and returns a 404 NOT FOUND response.
     *
     * @param ex the caught ContactNotFoundException
     * @return ResponseEntity containing error details with 404 status
     */
    @ExceptionHandler(value = ContactNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleMyCustomException(RuntimeException ex) {
        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.name())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles validation errors thrown by @Valid annotated request bodies.
     *
     * @param ex the MethodArgumentNotValidException containing validation errors
     * @return ResponseEntity with a map of validation error messages and 400 BAD REQUEST status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            errors.add(fieldError.getField() + " " + fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Utility method to wrap a list of error messages into a map under the "errors" key.
     *
     * @param errors list of error messages
     * @return map with key "errors" and corresponding list of messages
     */
    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

    /**
     * Handles login-related exceptions such as invalid email or password,
     * returning a 401 UNAUTHORIZED response.
     *
     * @param ex the caught RuntimeException related to login
     * @return ResponseEntity containing error details with 401 status
     */
    @ExceptionHandler({InvalidEmailException.class, InvalidPasswordException.class})
    public ResponseEntity<ErrorResponseDto> handleLoginExceptions(RuntimeException ex) {
        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .message(ex.getMessage())
                .status(HttpStatus.UNAUTHORIZED.name())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }


}
