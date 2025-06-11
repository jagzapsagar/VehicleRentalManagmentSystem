package com.example.VMSVehicleService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.VMSVehicleService.entity.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
	List<Vehicle> findByAvailableTrue();

}
