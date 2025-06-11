package com.example.VMSVehicleService.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.VMSVehicleService.dto.VehicleRequest;
import com.example.VMSVehicleService.dto.VehicleResponse;
import com.example.VMSVehicleService.entity.Vehicle;
import com.example.VMSVehicleService.repository.VehicleRepository;

@Service
public class VehicleService {

	@Autowired
	private VehicleRepository vehicleRepository;

	public VehicleResponse addVehicle(VehicleRequest request) {
		Vehicle v = new Vehicle();
		v.setBrand(request.getBrand());
		v.setModel(request.getModel());
		v.setType(request.getType());
		v.setPricePerDay(request.getPricePerDay());

		return mapToResponse(vehicleRepository.save(v));
	}

	public List<VehicleResponse> getAvailableVehicles() {
		return vehicleRepository.findByAvailableTrue().stream().map(this::mapToResponse).collect(Collectors.toList());
	}

	public VehicleResponse getVehicleById(Long id) {
		Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle not found"));
		return mapToResponse(vehicle);
	}

	public void updateAvailability(Long id, boolean available) {
		Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle not found"));
		vehicle.setAvailable(available);
		vehicleRepository.save(vehicle);
	}

	private VehicleResponse mapToResponse(Vehicle v) {
		VehicleResponse r = new VehicleResponse();
		r.setId(v.getId());
		r.setBrand(v.getBrand());
		r.setModel(v.getModel());
		r.setType(v.getType());
		r.setAvailable(v.isAvailable());
		r.setPricePerDay(v.getPricePerDay());
		return r;
	}

}
