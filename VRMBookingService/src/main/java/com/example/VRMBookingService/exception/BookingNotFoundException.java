package com.example.VRMBookingService.exception;

public class BookingNotFoundException extends RuntimeException {
	public BookingNotFoundException(String message) {
        super(message);
    }

}
