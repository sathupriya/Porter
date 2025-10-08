package com.example.Porter.PriceConfiguration;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.example.Porter.Constants.APIResponseDto;
import com.example.Porter.Constants.PriceConfigurationDto;


public interface PriceConfigurationService {

	public ResponseEntity<APIResponseDto<PriceConfigurationDto>> addPrice(PriceConfigurationDto dto);

	public ResponseEntity<APIResponseDto<PriceConfigurationDto>> updateStatus(Long id, boolean status);

	public ResponseEntity<APIResponseDto<PriceConfigurationDto>> viewById(Long id);
	
	public ResponseEntity<APIResponseDto<List<PriceConfigurationDto>>> getByStatus(boolean status);

    public ResponseEntity<APIResponseDto<Page<PriceConfigurationDto>>> getAllPrice(int page, int pageSize);
}
