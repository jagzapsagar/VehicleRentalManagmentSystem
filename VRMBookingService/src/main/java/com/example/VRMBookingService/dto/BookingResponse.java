package com.example.VRMBookingService.dto;

import java.time.LocalDate;

public class BookingResponse {

		private long bookingId;
	    private long userId;
	    private long vehicleId;
	    private LocalDate startDate;
	    private LocalDate endDate;
	    private String status;
		public long getBookingId() {
			return bookingId;
		}
		public void setBookingId(long bookingId) {
			this.bookingId = bookingId;
		}
		public long getUserId() {
			return userId;
		}
		public void setUserId(long userId) {
			this.userId = userId;
		}
		public long getVehicleId() {
			return vehicleId;
		}
		public void setVehicleId(long vehicleId) {
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
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
	    
	    

}
