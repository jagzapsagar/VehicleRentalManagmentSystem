package com.example.VRMBookingService.service;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.example.VRMBookingService.dto.BookingRequest;
import com.example.VRMBookingService.dto.BookingResponse;
import com.example.VRMBookingService.dto.VehicleResponse;
import com.example.VRMBookingService.entity.Booking;
import com.example.VRMBookingService.exception.BookingNotFoundException;
import com.example.VRMBookingService.exception.VehicleNotAvailableException;
import com.example.VRMBookingService.repository.BookingRepository;

//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private HttpServletRequest request;

	private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

	// private final String VEHICLE_SERVICE_URL = "http://localhost:8082/vehicles/";
	private final String VEHICLE_SERVICE_URL = "http://VMSVEHICLESERVICE/vehicles/";

	// private final String PAYMENT_SERVICE_URL =
	// "http://localhost:8087/payment/create-order";
	// private final String PAYMENT_SERVICE_URL =
	// "http://localhost:8088/payment/create-order";
	private final String PAYMENT_SERVICE_URL = "http://VRMPAYMENTSERVICE/payment/create-order";

	/*
	 * public List<BookingResponse> getAllBookings() { return
	 * bookingRepository.findAll() .stream() .map(this::mapToResponse)
	 * .collect(Collectors.toList()); }
	 */

	public Page<BookingResponse> getAllBookingsPaginated(int page, int size) {
		// Pageable pageable = PageRequest.of(page, size);
		Pageable pageable = PageRequest.of(page, size, Sort.by("userId").ascending());
		Page<Booking> bookingsPage = bookingRepository.findAll(pageable);
		if (bookingsPage.isEmpty()) {
			throw new BookingNotFoundException("No bookings found");
		}
		return bookingsPage.map(this::mapToResponse);
	}

	public BookingResponse bookVehicle(BookingRequest request) {
		VehicleResponse vehicle = fetchVehicleDetails(request.getVehicleId());

		//if (vehicle == null || !vehicle.isAvailable()) {
		//	throw new VehicleNotAvailableException("Vehicle not available");
		//}

		long rentalDays = ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate());
		double billAmount = rentalDays * vehicle.getPricePerDay();

		Map paymentResponse = createPayment(request.getUserId(), billAmount);
		System.out.println("-----BookingService.class-------************************--------------");
		System.out.println(request.getUserId());
		System.out.println("------------************************--------------");
		Booking booking = new Booking();
		booking.setUserId(request.getUserId());
		booking.setVehicleId(request.getVehicleId());
		booking.setStartDate(request.getStartDate());
		booking.setEndDate(request.getEndDate());
		booking.setStatus("BOOKED");
		bookingRepository.save(booking);

		//restTemplate.put(VEHICLE_SERVICE_URL + request.getVehicleId() + "/availability?available=false", null);

		return mapToResponse(booking);
	}

	public List<BookingResponse> getBookingsByUser(Long userId) {
		return bookingRepository.findByUserId(userId).stream().map(b -> {
			BookingResponse r = new BookingResponse();
			r.setBookingId(b.getBookingId());
			r.setUserId(b.getUserId());
			r.setVehicleId(b.getVehicleId());
			r.setStartDate(b.getStartDate());
			r.setEndDate(b.getEndDate());
			r.setStatus(b.getStatus());
			return r;
		}).collect(Collectors.toList());
	}

	public void cancelBooking(Long bookingId) {
		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new RuntimeException("Booking not found"));
		booking.setStatus("CANCELLED");
		bookingRepository.save(booking);

		// Notify vehicle service
		restTemplate.put(VEHICLE_SERVICE_URL + booking.getVehicleId() + "/availability?available=true", null);
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

	// Utility Methods
	// @CircuitBreaker(name = "vehicleService", fallbackMethod = "vehicleFallback")
	private VehicleResponse fetchVehicleDetails(Long vehicleId) {
		ResponseEntity<VehicleResponse> resp = restTemplate.exchange(VEHICLE_SERVICE_URL + vehicleId, HttpMethod.GET,
				null, VehicleResponse.class);
		return resp.getBody();
	}

	// @CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
	private Map createPayment(Long userId, double amount) {
		HashMap<String, Object> requestBody = new HashMap<>();
		requestBody.put("userId", userId);
		requestBody.put("amount", amount);
		requestBody.put("currency", "INR");
		requestBody.put("receipt", "test");

		String token = request.getHeader("Authorization");

		// reuse it
		// HttpEntity<String> entity = new HttpEntity<>(headers);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", token); // added for JWT
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

		ResponseEntity<Map> response = restTemplate.postForEntity(PAYMENT_SERVICE_URL, entity, Map.class);
		return response.getBody();
	}

	public VehicleResponse vehicleFallback(Long vehicleId, Throwable t) {
		System.out.println("🚫 Vehicle service failed: " + t.getMessage());
		throw new VehicleNotAvailableException("Fallback: Vehicle Service is down");
	}

	public Map paymentFallback(Long userId, double amount, Throwable t) {
		System.out.println("🚫 Payment service failed: " + t.getMessage());
		throw new RuntimeException("Fallback: Payment Service is unavailable");
	}

	public long countBookings() {
		// TODO Auto-generated method stub
		//return bookingRepository.count();
		return bookingRepository.countByStatus("BOOKED"); 
	}

	/*
	 * public BookingResponse bookVehicle(BookingRequest request) { VehicleResponse
	 * vehicle; try { ResponseEntity<VehicleResponse> resp =
	 * restTemplate.exchange(VEHICLE_SERVICE_URL + request.getVehicleId(),
	 * HttpMethod.GET, null, VehicleResponse.class); vehicle = resp.getBody(); }
	 * catch (HttpStatusCodeException ex) { // Service returned an error, e.g., 404
	 * or 503 throw new
	 * VehicleNotAvailableException("Exception :- Vehicle not available: " +
	 * ex.getStatusCode()); }
	 * 
	 * if (vehicle == null || !vehicle.isAvailable()) { throw new
	 * VehicleNotAvailableException("Exception :- Vehicle not available"); } //
	 * Calculated Bill // Calculate rental duration (inclusive) long daysBetween =
	 * ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate()); long
	 * rentalDays = daysBetween; // counting both start and end
	 * 
	 * double billAmount = rentalDays * vehicle.getPricePerDay();
	 * System.out.println("********************Calculated Bill: " + billAmount);
	 * 
	 * // Calling Payment Service HashMap<String, Object> requestBody = new
	 * HashMap<>(); requestBody.put("userId", request.getUserId());
	 * requestBody.put("amount", billAmount); requestBody.put("currency", "INR");
	 * requestBody.put("receipt", "test");
	 * 
	 * HttpHeaders header = new HttpHeaders();
	 * header.setContentType(MediaType.APPLICATION_JSON);
	 * 
	 * HttpEntity<Map<String, Object>> requestt = new HttpEntity<>(requestBody,
	 * header); logger.info(PAYMENT_SERVICE_URL + " Calling...");
	 * ResponseEntity<Map> reponse = restTemplate.postForEntity(PAYMENT_SERVICE_URL,
	 * requestt, Map.class);
	 * 
	 * // Create and save booking Booking booking = new Booking();
	 * booking.setUserId(request.getUserId());
	 * booking.setVehicleId(request.getVehicleId());
	 * booking.setStartDate(request.getStartDate());
	 * booking.setEndDate(request.getEndDate()); booking.setStatus("BOOKED");
	 * 
	 * Booking saved = bookingRepository.save(booking);
	 * 
	 * // Update vehicle availability restTemplate.put(VEHICLE_SERVICE_URL +
	 * request.getVehicleId() + "/availability?available=false", null);
	 * 
	 * return mapToResponse(booking);
	 * 
	 * }
	 */

}
