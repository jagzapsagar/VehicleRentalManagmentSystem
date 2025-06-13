package com.example.VMSVehicleService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.VMSVehicleService.dto.VehicleRequest;
import com.example.VMSVehicleService.dto.VehicleResponse;
import com.example.VMSVehicleService.service.VehicleService;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
	@Autowired
	private VehicleService vehicleService;

	@PostMapping
	public ResponseEntity<VehicleResponse> addVehicle(@RequestBody VehicleRequest request) {
		return ResponseEntity.ok(vehicleService.addVehicle(request));
	}

	@GetMapping("/available")
	public ResponseEntity<List<VehicleResponse>> getAvailableVehicles() {
		return ResponseEntity.ok(vehicleService.getAvailableVehicles());
	}

	@GetMapping("/{id}")
	public ResponseEntity<VehicleResponse> getVehicleById(@PathVariable Long id) {
		return ResponseEntity.ok(vehicleService.getVehicleById(id));
	}

	@PutMapping("/{id}/availability")
	public ResponseEntity<Void> updateAvailability(@PathVariable Long id, @RequestParam boolean available) {
		vehicleService.updateAvailability(id, available);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id){
		String message = vehicleService.deleteById(id);
		return ResponseEntity.ok().body(message); // HTTP 200 OK with success message
		
	}

}
