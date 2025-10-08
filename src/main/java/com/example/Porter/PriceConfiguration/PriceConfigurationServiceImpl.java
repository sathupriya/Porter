package com.example.Porter.PriceConfiguration;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Porter.Constants.APIResponseDto;
import com.example.Porter.Constants.PriceConfigurationDto;
import com.example.Porter.Exception.AlreadyExistsException;
import com.example.Porter.Exception.ResourceNotFoundException;
import com.example.Porter.Responsehandler.SuccessResponseHandler;
import com.example.Porter.Utils.MapperUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PriceConfigurationServiceImpl implements PriceConfigurationService {

	private final PriceConfigurationRepository priceRepo;

	@Override
	public ResponseEntity<APIResponseDto<PriceConfigurationDto>> addPrice(PriceConfigurationDto dto) {
		
		boolean existPrice = priceRepo.existsByBaseFareAndRatePerKgAndRatePerKm(dto.getBaseFare(), dto.getRatePerKg(), dto.getRatePerKm());
	
		if(existPrice) {
			throw new AlreadyExistsException("Price config already exists");
		}

		List<PriceConfigurationModel> price = priceRepo.findByIsActiveTrue();
		price.forEach(config -> {
			config.setActive(false);
		});
		priceRepo.saveAll(price);

		PriceConfigurationModel priceConfig = PriceConfigurationModel.builder().baseFare(dto.getBaseFare())
				.ratePerKg(dto.getRatePerKg()).ratePerKm(dto.getRatePerKm()).igstPer(dto.getIgstPer())
				.cgstPer(dto.getCgstPer()).sgstPer(dto.getSgstPer()).createdBy(dto.getCreatedBy()).isActive(true)
				.build();
		priceRepo.save(priceConfig);

		PriceConfigurationDto response = new PriceConfigurationDto();
		BeanUtils.copyProperties(priceConfig, response);

		return ResponseEntity.ok(SuccessResponseHandler.success(response, "Price configuration addedd successfully"));
	}

	@Override
	public ResponseEntity<APIResponseDto<PriceConfigurationDto>> updateStatus(Long id, boolean status) {

		Optional<PriceConfigurationModel> price = priceRepo.findById(id);

		if (price.isEmpty()) {
			throw new ResourceNotFoundException("Price configuration not found");
		}

		PriceConfigurationModel priceDetails = price.get();
		priceDetails.setActive(status);
		priceRepo.save(priceDetails);

		PriceConfigurationDto response = MapperUtils.map(priceDetails, PriceConfigurationDto.class);

		return ResponseEntity
				.ok(SuccessResponseHandler.success(response, "Price configuration fetched by ID successfully"));

	}

	@Override
	public ResponseEntity<APIResponseDto<PriceConfigurationDto>> viewById(Long id) {

		Optional<PriceConfigurationModel> price = priceRepo.findById(id);

		if (price.isEmpty()) {
			throw new ResourceNotFoundException("Price configuration not found");
		}

		PriceConfigurationModel priceDetails = price.get();

		PriceConfigurationDto response = MapperUtils.map(priceDetails, PriceConfigurationDto.class);

		return ResponseEntity
				.ok(SuccessResponseHandler.success(response, "Price configuration fetched by ID successfully"));
	}

	@Override
	public ResponseEntity<APIResponseDto<List<PriceConfigurationDto>>> getByStatus(boolean status) {

		List<PriceConfigurationModel> price = priceRepo.findByIsActive(status);
		if (price.isEmpty()) {
			throw new ResourceNotFoundException("Price configuration not found");
		}

		List<PriceConfigurationDto> response = MapperUtils.mapList(price, PriceConfigurationDto.class);

		return ResponseEntity
				.ok(SuccessResponseHandler.success(response, "Price configuration fetched by status successfully"));
	}

	@Override
	public ResponseEntity<APIResponseDto<Page<PriceConfigurationDto>>> getAllPrice(int page, int pageSize) {

		Pageable pageable = PageRequest.of(page, pageSize);
		Page<PriceConfigurationModel> content = priceRepo.findAllByOrderByPriceConfigIdDesc(pageable);

		if (content.isEmpty()) {
			throw new ResourceNotFoundException("No data found");
		}

		Page<PriceConfigurationDto> response = MapperUtils.mapPage(content, PriceConfigurationDto.class);
		return ResponseEntity.ok(SuccessResponseHandler.success(response, "All price configuration"));

	}

}
