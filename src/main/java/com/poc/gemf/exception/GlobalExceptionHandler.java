package com.poc.gemf.exception;

import com.poc.gemf.responsedto.ApiResponseDTO;
import com.poc.gemf.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 1. Handle "Product Not Found" (Custom Logic)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleResourceNotFound(ResourceNotFoundException ex) {
        log.warn("Resource missing: {}", ex.getMessage()); // Log internal details

        return new ResponseEntity<>(
                new ApiResponseDTO<>(Constants.FAILURE, ex.getMessage(), null),
                HttpStatus.NOT_FOUND
        );
    }

    // 2. Handle Validation Errors (e.g., @Valid failed)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleValidation(MethodArgumentNotValidException ex) {
        // Extract the specific field error (e.g. "Name cannot be empty")
        String errorMsg = ex.getBindingResult().getFieldError().getDefaultMessage();

        return new ResponseEntity<>(
                new ApiResponseDTO<>(Constants.FAILURE, "Validation Error: " + errorMsg, null),
                HttpStatus.BAD_REQUEST
        );
    }

    // 3. Handle Unexpected / System Errors (The "Catch-All")
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleGeneralException(Exception ex) {
        log.error("An unexpected system error occurred: ", ex);
        // SAFE: Send a generic message to the UI. Don't leak internals.
        return new ResponseEntity<>(
                new ApiResponseDTO<>(Constants.FAILURE, "An internal server error occurred. Please contact support.", null),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}