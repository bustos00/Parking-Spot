package com.parkingcontrol.parking.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkingcontrol.parking.dtos.ParkingSpotDto;
import com.parkingcontrol.parking.models.ParkingSpotModel;
import com.parkingcontrol.parking.services.ParkingSportServices;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {
	
	private @Autowired ParkingSportServices parkingSportServices;

	@PostMapping 
	public ResponseEntity<Object>saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto){ 
		if(parkingSportServices.existsByParkingSpotCar(parkingSpotDto.getLicensePlateCar())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("conflict: License Plate Car is already in use!"); 
		}
		if (parkingSportServices.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("conflict: Parking Spot is already in use");
		}
		if (parkingSportServices.existsByApartementAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("conflict: Parking Spot already registered fot this apartments/block");
		}
		
		var parkingSpotModel = new ParkingSpotModel();
		BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel); 
		parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));

		return ResponseEntity.status(HttpStatus.CREATED).body(parkingSportServices.save(parkingSpotModel)); 				 
	} 
	
	@GetMapping
	public ResponseEntity<List<ParkingSpotModel>> getAllParkingSpots(){
		return ResponseEntity.status(HttpStatus.OK).body(parkingSportServices.findAll());  
	}
	@GetMapping("/id")
	public ResponseEntity<Object> getOneParkingSpot(@PathVariable (value= "id") UUID id){
		Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSportServices.finById(id);
		if (!parkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found."); 
		}
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
	}
	@DeleteMapping("/id")
	public ResponseEntity<Object> deleteParkingSpot(@PathVariable (value= "id") UUID id){
		Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSportServices.finById(id);
		if (!parkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found."); 
		}
		parkingSportServices.delete(parkingSpotModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Parking Spot Deleted succefully.");
	}
	@PutMapping ("/{id}")
	public ResponseEntity<Object> updateParkingSpot (@PathVariable (value = "id") UUID id, @RequestBody @Valid ParkingSpotDto parkingSpotDto){
		 Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSportServices.finById(id);
		if (!parkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
		}
		
		var parkingSpotModel = new ParkingSpotModel();
		BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
		parkingSpotModel.setId(parkingSpotModelOptional.get().getId());
		parkingSpotModel.setRegistrationDate(parkingSpotModelOptional.get().getRegistrationDate());
	return ResponseEntity.status(HttpStatus.OK).body(parkingSportServices.save(parkingSpotModel));
	}
}
