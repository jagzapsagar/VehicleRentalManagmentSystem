package com.example.VRMBookingService.scheduler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.VRMBookingService.entity.Booking;
import com.example.VRMBookingService.repository.BookingRepository;

@Service
public class BookingScheduler {
	
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
    private RestTemplate restTemplate;
	
	@Scheduled(cron = "0 * * * * *") // Every day at midnight
    public void releaseVehiclesAfterBookingEnd() {
        LocalDate today = LocalDate.now();
        List<Booking> expiredBookings = bookingRepository.findByEndDateBeforeOrEndDateEquals(today);

        for (Booking booking : expiredBookings) {
            try {
                String updateUrl = "http://VMSVEHICLESERVICE/vehicles/" + booking.getVehicleId() + "/availability?available=true";
                restTemplate.put(updateUrl, null);
                System.out.println("✅ Vehicle ID " + booking.getVehicleId() + " marked as available.");
            } catch (Exception e) {
                System.out.println("❌ Failed to update vehicle " + booking.getVehicleId());
            }
        }
    }

}
