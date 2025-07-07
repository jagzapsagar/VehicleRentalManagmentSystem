package com.example.VMSVehicleService.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehicles")
public class Vehicle {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String brand;
	    private String model;
	    private String type;
	    private Double pricePerDay;
	    private boolean available;
	    @Column(name = "image_url", length = 1000)
	    private String imageUrl;

	    private LocalDateTime createdAt;
	    private LocalDateTime updatedAt;

	    @PrePersist
	    public void onCreate() {
	        createdAt = LocalDateTime.now();
	        updatedAt = createdAt;
	        available = true;
	    }

	    @PreUpdate
	    public void onUpdate() {
	        updatedAt = LocalDateTime.now();
	    }

		public Vehicle() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Vehicle(Long id, String brand, String model, String type, Double pricePerDay, boolean available,
				String imageUrl, LocalDateTime createdAt, LocalDateTime updatedAt) {
			super();
			this.id = id;
			this.brand = brand;
			this.model = model;
			this.type = type;
			this.pricePerDay = pricePerDay;
			this.available = available;
			this.imageUrl = imageUrl;
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getBrand() {
			return brand;
		}

		public void setBrand(String brand) {
			this.brand = brand;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public Double getPricePerDay() {
			return pricePerDay;
		}

		public void setPricePerDay(Double pricePerDay) {
			this.pricePerDay = pricePerDay;
		}

		public boolean isAvailable() {
			return available;
		}

		public void setAvailable(boolean available) {
			this.available = available;
		}

		public String getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}

		public LocalDateTime getUpdatedAt() {
			return updatedAt;
		}

		public void setUpdatedAt(LocalDateTime updatedAt) {
			this.updatedAt = updatedAt;
		}

		@Override
		public String toString() {
			return "Vehicle [id=" + id + ", brand=" + brand + ", model=" + model + ", type=" + type + ", pricePerDay="
					+ pricePerDay + ", available=" + available + ", imageUrl=" + imageUrl + ", createdAt=" + createdAt
					+ ", updatedAt=" + updatedAt + "]";
		}

		
	    
	    

}
