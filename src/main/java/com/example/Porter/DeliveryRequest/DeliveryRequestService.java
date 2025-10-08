package com.example.Porter.DeliveryRequest;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.example.Porter.Constants.APIResponseDto;
import com.example.Porter.Constants.DeliveryRequestDto;

public interface DeliveryRequestService {

	public ResponseEntity<APIResponseDto<DeliveryRequestDto>> addRequest(DeliveryRequestDto dto);
	
	public ResponseEntity<APIResponseDto<DeliveryRequestDto>> updateRequest(Long id,DeliveryRequestDto dto);

	public ResponseEntity<APIResponseDto<DeliveryRequestDto>> updateStatus(Long id, String status);

	public ResponseEntity<APIResponseDto<DeliveryRequestDto>> getById(Long id);

	public ResponseEntity<APIResponseDto<List<DeliveryRequestDto>>> getByActiveStatus(boolean status);

	public ResponseEntity<APIResponseDto<Page<DeliveryRequestDto>>> getAll(int page, int pageSize);

	
}
