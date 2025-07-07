package com.example.VMSVehicleService.service;

import java.util.List;
import java.util.NoSuchElementException;
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
		v.setImageUrl(request.getImageUrl()); 

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
		r.setImageUrl(v.getImageUrl());
		return r;
	}

	public String deleteById(long id) {
			// TODO Auto-generated method stub
			if(vehicleRepository.existsById(id)) {
				vehicleRepository.deleteById(id);
				return "Vehicle deleted successfully";
			}else {
	            throw new NoSuchElementException("Vehicle with ID " + id + " not found");
	        }
		
	}

	public List<VehicleResponse> getAllVehicles() {
		return vehicleRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
	}
	
	public Vehicle updateVehicle(Long id, Vehicle updatedVehicle) {
        Vehicle existing = vehicleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Vehicle not found with ID: " + id));

        existing.setBrand(updatedVehicle.getBrand());
        existing.setModel(updatedVehicle.getModel());
        existing.setType(updatedVehicle.getType());
        existing.setPricePerDay(updatedVehicle.getPricePerDay());
        existing.setAvailable(true);
        //existing.setImageUrl(updatedVehicle.getImageUrl());

        return vehicleRepository.save(existing);
    }
	
	public long countVehicles() {
	    return vehicleRepository.count();
	}

}
