package com.example.VRMBookingService.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.VRMBookingService.dto.BookingRequest;
import com.example.VRMBookingService.dto.BookingResponse;
import com.example.VRMBookingService.dto.VehicleResponse;
import com.example.VRMBookingService.entity.Booking;
import com.example.VRMBookingService.repository.BookingRepository;

@Service
public class BookingService {
	
	@Autowired
    private BookingRepository bookingRepository;
	
	@Autowired
    private RestTemplate restTemplate;
	
	private final String VEHICLE_SERVICE_URL = "http://localhost:8082/vehicles/";
	
	public List<BookingResponse> getAllBookings() {
	    return bookingRepository.findAll()
	        .stream()
	        .map(this::mapToResponse)
	        .collect(Collectors.toList());
	}

	
	public BookingResponse bookVehicle(BookingRequest request) {
		
		// Step 1: Fetch vehicle details
        VehicleResponse vehicle = restTemplate.getForObject(
            VEHICLE_SERVICE_URL + request.getVehicleId(),
            VehicleResponse.class
        );

        if (vehicle == null || !vehicle.isAvailable()) {
            throw new RuntimeException("Vehicle not available");
        }
		
        Booking booking = new Booking();
        booking.setUserId(request.getUserId());
        booking.setVehicleId(request.getVehicleId());
        booking.setStartDate(request.getStartDate());
        booking.setEndDate(request.getEndDate());
        booking.setStatus("BOOKED");

        Booking saved = bookingRepository.save(booking);

        BookingResponse response = new BookingResponse();
        response.setBookingId(saved.getBookingId());
        response.setUserId(saved.getUserId());
        response.setVehicleId(saved.getVehicleId());
        response.setStartDate(saved.getStartDate());
        response.setEndDate(saved.getEndDate());
        response.setStatus(saved.getStatus());
        return response;
    }

    public List<BookingResponse> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId)
                .stream()
                .map(b -> {
                    BookingResponse r = new BookingResponse();
                    r.setBookingId(b.getBookingId());
                    r.setUserId(b.getUserId());
                    r.setVehicleId(b.getVehicleId());
                    r.setStartDate(b.getStartDate());
                    r.setEndDate(b.getEndDate());
                    r.setStatus(b.getStatus());
                    return r;
                })
                .collect(Collectors.toList());
    }

    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);
    }
    
    private BookingResponse mapToResponse(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setBookingId(booking.getBookingId());
        response.setUserId(booking.getUserId());
        response.setVehicleId(booking.getVehicleId());
        response.setStartDate(booking.getStartDate());
        response.setEndDate(booking.getEndDate());
        response.setStatus(booking.getStatus());
        return response;
    }


}
