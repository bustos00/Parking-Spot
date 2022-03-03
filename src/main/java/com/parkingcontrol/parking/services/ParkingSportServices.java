package com.parkingcontrol.parking.services;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parkingcontrol.parking.models.ParkingSpotModel;
import com.parkingcontrol.parking.repositories.ParkingSpotRepository;

@Service 
public class ParkingSportServices {

	private @Autowired ParkingSpotRepository parkingSpotRepository;

	@Transactional
	public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
		return parkingSpotRepository.save(parkingSpotModel); 
	}

	public boolean existsByParkingSpotCar(String licensePlateCar) {
		return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar); 
	}

	public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
		
		return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber); 
	}

	public boolean existsByApartementAndBlock(String apartment, String block) {
		
		return parkingSpotRepository.existsByApartmentAndBlock(apartment, block); 
	}

	public List<ParkingSpotModel> findAll() {
		
		return parkingSpotRepository.findAll(); 
	}
	public Optional<ParkingSpotModel> finById(UUID id){
		return parkingSpotRepository.findById(id); 
	}

	@Transactional 
	public void delete(ParkingSpotModel parkingSpotModel) {
		// TODO Auto-generated method stub
		 parkingSpotRepository.delete(parkingSpotModel);
	}
}
