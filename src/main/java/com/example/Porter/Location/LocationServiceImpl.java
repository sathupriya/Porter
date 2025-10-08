package com.example.Porter.Location;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Porter.Constants.APIResponseDto;
import com.example.Porter.Constants.LocationDto;
import com.example.Porter.Exception.AlreadyExistsException;
import com.example.Porter.Responsehandler.SuccessResponseHandler;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {

	private final CountryRepository countryRepo;
	private final StateRepository stateRepo;
	private final CityRepository cityRepo;
	private final LocationRepository locationRepo;


	@Override
	public ResponseEntity<APIResponseDto<LocationDto>> addCountry(LocationDto dto) {
		countryRepo.findByCountryCodeOrCountryName(dto.getCountryCode(), dto.getCountryName()).ifPresent(country -> {
			throw new AlreadyExistsException("Country already exists!");
		});

		CountryModel model = CountryModel.builder().countryName(dto.getCountryName()).countryCode(dto.getCountryCode())
				.createdBy(dto.getCreatedBy()).activeStatus(true).build();
	
		CountryModel saved = countryRepo.save(model);

		LocationDto responseDto = new LocationDto();
		BeanUtils.copyProperties(saved, responseDto);
		return ResponseEntity.ok(SuccessResponseHandler.success(responseDto, "Country added successfully"));
	}

	@Override
	public ResponseEntity<APIResponseDto<LocationDto>> updateCountry(Long id, LocationDto dto) {
		Optional<CountryModel> existCountry = countryRepo.findById(id);
		Optional<CountryModel> country = countryRepo.findByCountryCodeOrCountryName(dto.getCountryCode(),
				dto.getCountryName());

		if (existCountry.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.noDataFound("Country not found"));
		}

		if (country.isPresent() && !country.get().getCountryId().equals(id)) {
			throw new AlreadyExistsException("Country already exists with name");
		}
		CountryModel details = existCountry.get();
		details.setCountryName(dto.getCountryName());
		details.setCountryCode(dto.getCountryCode());
		details.setModifiedBy(dto.getModifiedBy());
		CountryModel updated = countryRepo.save(details);

		LocationDto saved = new LocationDto();
		BeanUtils.copyProperties(updated, saved);

		return ResponseEntity.ok(SuccessResponseHandler.success(saved, "Country updated successfully"));

	}

	@Override
	public ResponseEntity<APIResponseDto<LocationDto>> updateStatus(Long id, boolean status) {
		Optional<CountryModel> existCountry = countryRepo.findById(id);
		if (existCountry.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.noDataFound("Country not found"));
		}
		CountryModel details = existCountry.get();
		details.setActiveStatus(status);
		countryRepo.save(details);

		return ResponseEntity.ok(SuccessResponseHandler.success(
				status == true ? "Country status activated successfully" : "Country status deactivated successfully"));

	}

	@Override
	public ResponseEntity<APIResponseDto<LocationDto>> viewByCountryId(Long id) {
		Optional<CountryModel> existCountry = countryRepo.findById(id);
		if (existCountry.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.noDataFound("Country not found"));
		}

		CountryModel details = existCountry.get();

		LocationDto saved = new LocationDto();
		BeanUtils.copyProperties(details, saved);

		return ResponseEntity.ok(SuccessResponseHandler.success(saved, "Country fetched By ID successfully"));
	}

	@Override
	public ResponseEntity<APIResponseDto<Page<LocationDto>>> getAllCountry(int page, int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<CountryModel> content = countryRepo.findAllByOrderByCountryIdDesc(pageable);

		if (content.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.noDataFound("No data found"));
		}

		Page<LocationDto> dtoList = content.map(model -> {
			LocationDto dto = new LocationDto();
			BeanUtils.copyProperties(model, dto);
			return dto;
		});

		return ResponseEntity.ok(SuccessResponseHandler.success(dtoList, "All Country details"));
	}

	@Override
	public ResponseEntity<APIResponseDto<LocationDto>> addState(LocationDto dto) {
		Optional<CountryModel> existCountry = countryRepo.findById(dto.getCountryId());
		Optional<StateModel> existsState = stateRepo.findByStateNameOrStateCode(dto.getStateName(), dto.getStateCode());

		if (existCountry.isEmpty() && !existCountry.get().isActiveStatus()) {
			String message = existCountry.isEmpty() ? "Country not found" : "Country is in inactive status";
			return ResponseEntity.ok(SuccessResponseHandler.noDataFound(message));
		}

		if (existsState.isPresent()) {
			throw new AlreadyExistsException("State Name already exists!");
		}

		StateModel state = StateModel.builder().activeStatus(true).stateName(dto.getStateName())
				.stateCode(dto.getStateCode()).createdBy(dto.getCreatedBy()).country(existCountry.get()).build();
		StateModel saved = stateRepo.save(state);
		LocationDto responseDto = new LocationDto();
		BeanUtils.copyProperties(saved, responseDto);

		return ResponseEntity.ok(SuccessResponseHandler.success(responseDto, "State created successfully"));
	}

	@Override
	public ResponseEntity<APIResponseDto<LocationDto>> updateState(Long id, LocationDto dto) {
		Optional<StateModel> existState = stateRepo.findById(id);
		Optional<StateModel> duplicateState = stateRepo.findByStateNameOrStateCode(dto.getStateName(),
				dto.getStateCode());

		if (existState.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.success("State not found"));
		}

		if (duplicateState.isPresent()) {
			return ResponseEntity.ok(SuccessResponseHandler.success("State already exists"));
		}

		StateModel state = existState.get();
		state.setStateCode(dto.getStateCode());
		state.setStateName(dto.getStateName());
		state.setModifiedBy(dto.getModifiedBy());
		StateModel updated = stateRepo.save(state);

		LocationDto saved = new LocationDto();
		BeanUtils.copyProperties(updated, saved);
		return ResponseEntity.ok(SuccessResponseHandler.success(saved, "State details updated successfully"));

	}

	@Override
	public ResponseEntity<APIResponseDto<LocationDto>> updateStateStatus(Long id, boolean status) {
		Optional<StateModel> existState = stateRepo.findById(id);
		if (existState.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.success("State not found"));
		}

		StateModel state = existState.get();
		state.setActiveStatus(status);
		stateRepo.save(state);

		return ResponseEntity.ok(SuccessResponseHandler.success(
				status == true ? "State status activated successfully" : "State status deactivated successfully"));
	}

	@Override
	public ResponseEntity<APIResponseDto<LocationDto>> viewByStateId(Long id) {
		Optional<StateModel> existState = stateRepo.findById(id);
		if (existState.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.success("State not found"));
		}

		StateModel state = existState.get();

		LocationDto saved = new LocationDto();
		BeanUtils.copyProperties(state, saved);

		return ResponseEntity.ok(SuccessResponseHandler.success(saved, "State fetched by ID successfully"));
	}

	@Override
	public ResponseEntity<APIResponseDto<Page<LocationDto>>> getAllState(int page, int pageSize) {

		Pageable pageable = PageRequest.of(page, pageSize);
		Page<StateModel> content = stateRepo.findAllByOrderByStateIdDesc(pageable);

		if (content.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.success("No data found"));
		}

		Page<LocationDto> dtoList = content.map(model -> {
			LocationDto dto = new LocationDto();
			BeanUtils.copyProperties(model, dto);
			return dto;
		});

		return ResponseEntity.ok(SuccessResponseHandler.success(dtoList, "All state details"));
	}

	@Override
	public ResponseEntity<APIResponseDto<LocationDto>> addCity(LocationDto dto) {
		Optional<StateModel> state = stateRepo.findById(dto.getStateId());
		Optional<CityModel> existCity = cityRepo.findByCityCodeOrCityName(dto.getCityCode(), dto.getCityName());

		if (state.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.success("No data found"));
		}

		if (existCity.isPresent()) {
			throw new AlreadyExistsException("City already exists");
		}

		CityModel city = CityModel.builder().cityCode(dto.getCityCode()).cityName(dto.getCityName())
				.createdBy(dto.getCreatedBy()).state(state.get()).build();
		CityModel saved = cityRepo.save(city);
		LocationDto responseDto = new LocationDto();
		BeanUtils.copyProperties(saved, responseDto);
		return ResponseEntity.ok(SuccessResponseHandler.success(responseDto, "City added successfully"));

	}

	@Override
	public ResponseEntity<APIResponseDto<LocationDto>> updateCity(Long id, LocationDto dto) {
		Optional<CityModel> existCity = cityRepo.findById(id);
		Optional<CityModel> duplicateCity = cityRepo.findByCityCodeOrCityName(dto.getCityCode(), dto.getCityName());
		if (existCity.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.success("No data found"));
		}

		if (duplicateCity.isPresent()) {
			throw new AlreadyExistsException("City already exists");
		}
		CityModel city = existCity.get();
		city.setCityCode(dto.getCityCode());
		city.setCityName(dto.getCityName());
		city.setActiveStatus(true);
		city.setModifiedBy(dto.getModifiedBy());
		CityModel saved = cityRepo.save(city);
		LocationDto responseDto = new LocationDto();
		BeanUtils.copyProperties(saved, responseDto);
		return ResponseEntity.ok(SuccessResponseHandler.success(responseDto, "City updated successfully"));
	}

	@Override
	public ResponseEntity<APIResponseDto<LocationDto>> updateCityStatus(Long id, boolean status) {
		Optional<CityModel> existCity = cityRepo.findById(id);
		if (existCity.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.success("City not found"));
		}

		CityModel city = existCity.get();
		city.setActiveStatus(status);
		cityRepo.save(city);

		return ResponseEntity.ok(SuccessResponseHandler.success(
				status == true ? "City status activated successfully" : "City status deactivated successfully"));
	}

	@Override
	public ResponseEntity<APIResponseDto<LocationDto>> viewByCityId(Long id) {
		Optional<CityModel> existCity = cityRepo.findById(id);
		if (existCity.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.success("City not found"));
		}

		CityModel city = existCity.get();

		LocationDto saved = new LocationDto();
		BeanUtils.copyProperties(city, saved);

		return ResponseEntity.ok(SuccessResponseHandler.success(saved, "City fetched by ID successfully"));
	}

	@Override
	public ResponseEntity<APIResponseDto<Page<LocationDto>>> getAllCity(int page, int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<CityModel> content = cityRepo.findAllByOrderByCityIdDesc(pageable);

		if (content.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.success("No data found"));
		}

		Page<LocationDto> listOfValues = content.map(model -> {
			LocationDto dto = new LocationDto();
			BeanUtils.copyProperties(model, dto);
			return dto;
		});

		return ResponseEntity.ok(SuccessResponseHandler.success(listOfValues, "All cities fetched successfully"));
	}

	@Override
	public ResponseEntity<APIResponseDto<LocationDto>> addLocation(LocationDto dto) {
		Optional<CityModel> existCity = cityRepo.findById(dto.getCityId());
		Optional<LocationModel> existLocation = locationRepo.findByPincode(dto.getPincode());

		if (existCity.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.success("City not found"));
		}

		if (existLocation.isPresent()) {
			throw new AlreadyExistsException("Location already exists");
		}

		LocationModel location = LocationModel.builder().activeStatus(true).createdBy(dto.getCreatedBy())
				.landmark(dto.getLandMark()).pincode(dto.getPincode()).city(existCity.get()).build();
		LocationDto responseDto = new LocationDto();
		BeanUtils.copyProperties(location, responseDto);

		return ResponseEntity.ok(SuccessResponseHandler.success(responseDto, "Location added successfully"));

	}

	@Override
	public ResponseEntity<APIResponseDto<LocationDto>> updateLocation(Long id, LocationDto dto) {
		Optional<LocationModel> existLocation = locationRepo.findById(id);
		// Optional<LocationModel> existPincode =
		// locationRepo.findByPincode(dto.getPincode());
		if (existLocation.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.success("Location not found"));
		}

		LocationModel details = existLocation.get();
		details.setLandmark(dto.getLandMark());
		details.setPincode(dto.getPincode());
		details.setModifiedBy(dto.getModifiedBy());
		LocationModel saved = locationRepo.save(details);
		LocationDto responseDto = new LocationDto();
		BeanUtils.copyProperties(saved, responseDto);

		return ResponseEntity.ok(SuccessResponseHandler.success(responseDto, "Location updated successsfully"));

	}

	@Override
	public ResponseEntity<APIResponseDto<LocationDto>> updateLocationStatus(Long id, boolean status) {
		Optional<LocationModel> location = locationRepo.findById(id);
		if (location.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.success("Location not found"));
		}

		LocationModel details = location.get();
		details.setActiveStatus(status);
		locationRepo.save(details);
		return ResponseEntity.ok(SuccessResponseHandler
				.success(status == true ? "Location activated successfully" : "Location inactivated successfully"));

	}

	@Override
	public ResponseEntity<APIResponseDto<LocationDto>> viewByLocationId(Long id) {
		Optional<LocationModel> location = locationRepo.findById(id);
		if (location.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.success("Location not found"));
		}

		LocationModel details = location.get();
		LocationDto responseDto = new LocationDto();
		BeanUtils.copyProperties(details, responseDto);

		return ResponseEntity.ok(SuccessResponseHandler.success(responseDto, "Location fetched by Id successfully"));
	}

	@Override
	public ResponseEntity<APIResponseDto<Page<LocationDto>>> getALlLocation(int page, int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<LocationModel> content = locationRepo.findAllByOrderByLocationIdDesc(pageable);
		
		if(content.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.success("No Data found"));
		}
		
		Page<LocationDto> dtoList = content.map(model -> {
			LocationDto dto = new LocationDto();
			BeanUtils.copyProperties(model, dto);
			return dto;
		});
		
			return ResponseEntity.ok(SuccessResponseHandler.success(dtoList,"All location fetched successfully"));
	}

}
