package com.example.VRMBookingService.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.example.VRMBookingService.dto.ErrorResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(VehicleNotAvailableException.class)
	public ResponseEntity<ErrorResponse> handleVehicleNotAvailable(
	        VehicleNotAvailableException ex,
	        WebRequest request) {

	    ErrorResponse response = new ErrorResponse(
	        LocalDateTime.now(),
	        ex.getMessage(),
	        request.getDescription(false)
	    );
	    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handleValidationErrors(MethodArgumentNotValidException ex) {
	    Map<String,String> errors = new HashMap<>();
	    ex.getBindingResult().getFieldErrors()
	      .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
	    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	
	@ExceptionHandler(BookingNotFoundException.class)
	public ResponseEntity<String> handleBookingNotFound(BookingNotFoundException ex, WebRequest request) {
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex, WebRequest request) {
        return new ResponseEntity<>("Something went wrong: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
