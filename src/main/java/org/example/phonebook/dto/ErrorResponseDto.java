package org.example.phonebook.dto;

import lombok.Builder;
import lombok.Data;
/**
 * Standard error response DTO for API error handling.
 *
 * <p>Encapsulates the error message, HTTP status text, and status code
 * returned to the client in case of an exception or failure.
 */
@Data
@Builder
public class ErrorResponseDto {

    private String message;
    private String status;
    private int statusCode;

}
