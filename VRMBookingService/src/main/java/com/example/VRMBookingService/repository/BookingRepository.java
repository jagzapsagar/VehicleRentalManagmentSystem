package com.example.VRMBookingService.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.VRMBookingService.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
	List<Booking> findByUserId(Long userId);
	//List<Booking> findByEndDateBeforeOrEndDateEquals(LocalDate date);
	long countByStatus(String status);
	List<Booking> findByEndDateLessThanEqual(LocalDate endDate);
	@Query("SELECT b FROM Booking b WHERE b.vehicleId = :vehicleId AND b.status = 'BOOKED' AND " +
		       "(:startDate <= b.endDate AND :endDate >= b.startDate)")
		List<Booking> findOverlappingBookings(
		    @Param("vehicleId") Long vehicleId,
		    @Param("startDate") LocalDate startDate,
		    @Param("endDate") LocalDate endDate
		);


	//List<Booking> findByUserEmail(String email);


}
