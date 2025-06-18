package com.example.VRMBookingService.service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.example.VRMBookingService.dto.BookingRequest;
import com.example.VRMBookingService.dto.BookingResponse;
import com.example.VRMBookingService.dto.VehicleResponse;
import com.example.VRMBookingService.entity.Booking;
import com.example.VRMBookingService.exception.BookingNotFoundException;
import com.example.VRMBookingService.exception.VehicleNotAvailableException;
import com.example.VRMBookingService.repository.BookingRepository;

@Service
public class BookingService {
	
	@Autowired
    private BookingRepository bookingRepository;
	
	@Autowired
    private RestTemplate restTemplate;
	
	private final String VEHICLE_SERVICE_URL = "http://localhost:8082/vehicles/";
	
	/*
	 * public List<BookingResponse> getAllBookings() { return
	 * bookingRepository.findAll() .stream() .map(this::mapToResponse)
	 * .collect(Collectors.toList()); }
	 */

	public Page<BookingResponse> getAllBookingsPaginated(int page, int size) {
	    //Pageable pageable = PageRequest.of(page, size);
	    Pageable pageable = PageRequest.of(page, size, Sort.by("userId").ascending());
	    Page<Booking> bookingsPage = bookingRepository.findAll(pageable);
	    if (bookingsPage.isEmpty()) {
	        throw new BookingNotFoundException("No bookings found");
	    }
	    return bookingsPage.map(this::mapToResponse);
	}
	
	public BookingResponse bookVehicle(BookingRequest request) {
		
		/*
		 * // Fetch vehicle details VehicleResponse vehicle = restTemplate.getForObject(
		 * VEHICLE_SERVICE_URL + request.getVehicleId(), VehicleResponse.class );
		 */
		
		VehicleResponse vehicle;
	    try {
	        ResponseEntity<VehicleResponse> resp = restTemplate.exchange(
	            VEHICLE_SERVICE_URL + request.getVehicleId(),
	            HttpMethod.GET,
	            null,
	            VehicleResponse.class
	        );
	        vehicle = resp.getBody();
	    } catch (HttpStatusCodeException ex) {
	        // Service returned an error, e.g., 404 or 503
	        throw new VehicleNotAvailableException(
	            "Exception :- Vehicle not available: " + ex.getStatusCode()
	        );
	    }

        if (vehicle == null || !vehicle.isAvailable()) {
            throw new VehicleNotAvailableException("Exception :- Vehicle not available");
        }
      // Calculated Bill
     // Calculate rental duration (inclusive)
        long daysBetween = ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate());
        long rentalDays = daysBetween; // counting both start and end

        double billAmount = rentalDays * vehicle.getPricePerDay();
        System.out.println("********************Calculated Bill: "+billAmount);
        
     // Create and save booking
        Booking booking = new Booking();
        booking.setUserId(request.getUserId());
        booking.setVehicleId(request.getVehicleId());
        booking.setStartDate(request.getStartDate());
        booking.setEndDate(request.getEndDate());
        booking.setStatus("BOOKED");

        Booking saved = bookingRepository.save(booking);
        
     // Update vehicle availability
        restTemplate.put(
            VEHICLE_SERVICE_URL + request.getVehicleId() + "/availability?available=false",
            null
        );
        
        return mapToResponse(booking);
        
		/*
		 * BookingResponse response = new BookingResponse();
		 * response.setBookingId(saved.getBookingId());
		 * response.setUserId(saved.getUserId());
		 * response.setVehicleId(saved.getVehicleId());
		 * response.setStartDate(saved.getStartDate());
		 * response.setEndDate(saved.getEndDate());
		 * response.setStatus(saved.getStatus()); return response;
		 */
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
        
     // Notify vehicle service
        restTemplate.put(
            VEHICLE_SERVICE_URL + booking.getVehicleId() + "/availability?available=true",
            null
        );
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
