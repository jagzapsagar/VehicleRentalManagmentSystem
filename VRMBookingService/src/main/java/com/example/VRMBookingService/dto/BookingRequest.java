package com.example.VRMBookingService.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public class BookingRequest {
	@NotNull(message = "User ID is mandatory")
	private Long userId;
	@NotNull(message = "vehicle ID is mandatory")
    private Long vehicleId;
	@NotNull(message = "Start date is required")
	@FutureOrPresent(message = "Start date must be today or in the future")
    private LocalDate startDate;
	@NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    private LocalDate endDate;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
    
    

}
