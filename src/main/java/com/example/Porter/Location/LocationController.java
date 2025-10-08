package com.example.Porter.Location;


import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Porter.Constants.APIResponseDto;
import com.example.Porter.Constants.LocationDto;

@RestController
@RequestMapping("/location")
public class LocationController {

	private final LocationService service;
	
	private LocationController(LocationService service) {
		this.service=service;
	}
	
	@PostMapping("/addCountry")
	public ResponseEntity<APIResponseDto<LocationDto>> addCountry(@RequestBody LocationDto dto){
		return service.addCountry(dto);
	}
	
	@PutMapping("/updateCountry/{id}")
	public ResponseEntity<APIResponseDto<LocationDto>> updateCountry(@PathVariable Long id,@RequestBody LocationDto dto){
		return service.updateCountry(id, dto);
	}
	
	@GetMapping("/updateCountryStatus/{id}/{status}")
	public ResponseEntity<APIResponseDto<LocationDto>> updateStatus(@PathVariable Long id,@PathVariable boolean status){
		return service.updateStatus(id, status);
	}
	
	@GetMapping("/viewByCountryId/{id}")
	public ResponseEntity<APIResponseDto<LocationDto>> viewByCountryId(@PathVariable Long id){
		return service.viewByCountryId(id);
	}
	
	@GetMapping("/getAllCountry/{page}/{pageSize}")
	public ResponseEntity<APIResponseDto<Page<LocationDto>>> getAllCountry(@PathVariable int page,@PathVariable int pageSize){
		return service.getAllCountry(page, pageSize);
	}
	
	@PostMapping("/addState")
	public ResponseEntity<APIResponseDto<LocationDto>> addState(@RequestBody LocationDto dto){
		return service.addState(dto);
	}
	
	@PutMapping("/updateState/{id}")
	public ResponseEntity<APIResponseDto<LocationDto>> updateState(@PathVariable Long id,@RequestBody LocationDto dto){
		return service.updateState(id, dto);
	}
	
	@GetMapping("/updateStateStatus/{id}/{status}")
	public ResponseEntity<APIResponseDto<LocationDto>> updateStateStatus(@PathVariable Long id,@PathVariable boolean status){
		return service.updateStateStatus(id, status);
	}
	
	@GetMapping("/viewByStateId/{id}")
	public ResponseEntity<APIResponseDto<LocationDto>> viewByStateId(@PathVariable Long id){
		return service.viewByStateId(id);
	}
	
	@GetMapping("/getAllState/{page}/{pageSize}")
	public ResponseEntity<APIResponseDto<Page<LocationDto>>> getAllState(@PathVariable int page,@PathVariable int pageSize){
		return service.getAllState(page, pageSize);
	}
	
	@PostMapping("/addCity")
	public ResponseEntity<APIResponseDto<LocationDto>> addCity(@RequestBody LocationDto dto){
		return service.addCity(dto);
	}
	
	@PutMapping("/updateCity/{id}")
	public ResponseEntity<APIResponseDto<LocationDto>> updateCity(@PathVariable Long id,@RequestBody LocationDto dto){
		return service.updateCity(id, dto);
	}
	
	@GetMapping("/updateCityStatus/{id}/{status}")
	public ResponseEntity<APIResponseDto<LocationDto>> updateCityStatus(@PathVariable Long id,@PathVariable boolean status){
		return service.updateCityStatus(id, status);
	}
	
	@GetMapping("/viewCityById/{id}")
	public ResponseEntity<APIResponseDto<LocationDto>> viewByCityId(@PathVariable Long id){
		return service.viewByCityId(id);
	}
	
	@GetMapping("/getAllCity/{page}/{pageSize}")
	public ResponseEntity<APIResponseDto<Page<LocationDto>>> getAllCity(@PathVariable int page,@PathVariable int pageSize){
		return service.getAllCity(page, pageSize);
	}
	
	@PostMapping("/addLocation")
	public ResponseEntity<APIResponseDto<LocationDto>> addLocation(@RequestBody LocationDto dto){
		return service.addLocation(dto);
	}
	
	@PutMapping("/updateLocation/{id}")
	public ResponseEntity<APIResponseDto<LocationDto>> updateLocation(@PathVariable Long id,@RequestBody LocationDto dto){
		return service.updateLocation(id, dto);
	}
	
	@GetMapping("/updateLocationStatus/{id}/{status}")
	public ResponseEntity<APIResponseDto<LocationDto>> updateLocationStatus(@PathVariable Long id,@PathVariable boolean status){
		return service.updateLocationStatus(id, status);
	}
	
	@GetMapping("/viewLocationById/{id}")
	public ResponseEntity<APIResponseDto<LocationDto>> viewByLocationId(@PathVariable Long id){
		return service.viewByLocationId(id);
	}
	
	@GetMapping("/getAllLocation/{page}/{pageSize}")
	public ResponseEntity<APIResponseDto<Page<LocationDto>>> getALlLocation(@PathVariable int page,@PathVariable int pageSize){
		return service.getALlLocation(page, pageSize);
	}
	
}
