package com.example.Porter.Location;


import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.example.Porter.Constants.APIResponseDto;
import com.example.Porter.Constants.LocationDto;


public interface LocationService {

	
	public ResponseEntity<APIResponseDto<LocationDto>> addCountry(LocationDto dto);
	
	public ResponseEntity<APIResponseDto<LocationDto>> updateCountry(Long id,LocationDto dto);
	
	public ResponseEntity<APIResponseDto<LocationDto>> updateStatus(Long id,boolean status);
	
	public ResponseEntity<APIResponseDto<LocationDto>> viewByCountryId(Long id);
	
	public ResponseEntity<APIResponseDto<Page<LocationDto>>> getAllCountry(int page,int pageSize);
	
	public ResponseEntity<APIResponseDto<LocationDto>> addState(LocationDto dto);
	
	public ResponseEntity<APIResponseDto<LocationDto>> updateState(Long id,LocationDto dto);
	
	public ResponseEntity<APIResponseDto<LocationDto>> updateStateStatus(Long id,boolean status);
	
	public ResponseEntity<APIResponseDto<LocationDto>> viewByStateId(Long id);
	
	public ResponseEntity<APIResponseDto<Page<LocationDto>>> getAllState(int page,int pageSize);
	
	public ResponseEntity<APIResponseDto<LocationDto>> addCity(LocationDto dto);
	
	public ResponseEntity<APIResponseDto<LocationDto>> updateCity(Long id,LocationDto dto);
	
	public ResponseEntity<APIResponseDto<LocationDto>> updateCityStatus(Long id,boolean status);
	
	public ResponseEntity<APIResponseDto<LocationDto>> viewByCityId(Long id);
	
	public ResponseEntity<APIResponseDto<Page<LocationDto>>> getAllCity(int page,int pageSize);
	
	public ResponseEntity<APIResponseDto<LocationDto>> addLocation(LocationDto dto);
	
	public ResponseEntity<APIResponseDto<LocationDto>> updateLocation(Long id,LocationDto dto);
	
	public ResponseEntity<APIResponseDto<LocationDto>> updateLocationStatus(Long id,boolean status);
	
	public ResponseEntity<APIResponseDto<LocationDto>> viewByLocationId(Long id);
	
	public ResponseEntity<APIResponseDto<Page<LocationDto>>> getALlLocation(int page,int pageSize);
	
	
	
}
