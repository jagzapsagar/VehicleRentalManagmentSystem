package com.example.VRMUserService.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.example.VRMUserService.dto.ExceptionResponse;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;


@ControllerAdvice
public class GlobleExceptionHandler {
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ExceptionResponse> UserAlreadyExistsException(UserAlreadyExistsException e, WebRequest request){
		
		ExceptionResponse response = new ExceptionResponse(
                LocalDateTime.now(),
                e.getMessage(),
                request.getDescription(false)
        );
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		
	}

}
