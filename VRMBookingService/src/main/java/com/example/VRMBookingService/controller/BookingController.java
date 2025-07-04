package com.example.VRMBookingService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.VRMBookingService.dto.BookingRequest;
import com.example.VRMBookingService.dto.BookingResponse;
import com.example.VRMBookingService.service.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/booking")
@CrossOrigin(origins = "http://localhost:4200")
public class BookingController {
	
	@Autowired
    private BookingService bookingService;
	
	/*
	 * @GetMapping public List<BookingResponse> getAllBookings() { return
	 * bookingService.getAllBookings(); }
	 */
	
	@GetMapping
	public Page<BookingResponse> getAllBookings(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "2") int size) {
	    return bookingService.getAllBookingsPaginated(page, size);
	}

	
	@PostMapping
    public BookingResponse createBooking(@RequestBody @Valid BookingRequest request) {
        return bookingService.bookVehicle(request);
    }

    @GetMapping("/user/{userId}")
    public List<BookingResponse> getUserBookings(@PathVariable Long userId) {
        return bookingService.getBookingsByUser(userId);
    	//return null;
    }

    @PutMapping("/cancel/{bookingId}")
    public void cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
    }
    
    @GetMapping("/count")
	public long getBookingCount() {
	    return bookingService.countBookings();  // This should return a long
	}

}
