package com.example.VRMBookingService.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.VRMBookingService.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
	List<Booking> findByUserId(Long userId);
	//List<Booking> findByUserEmail(String email);


}
