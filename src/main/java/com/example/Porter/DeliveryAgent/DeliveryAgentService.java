package com.example.Porter.DeliveryAgent;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.example.Porter.Constants.APIResponseDto;
import com.example.Porter.Constants.DeliveryAgentDto;

public interface DeliveryAgentService {

	public ResponseEntity<APIResponseDto<DeliveryAgentDto>> addDeliveryAgent(DeliveryAgentDto dto);

	public ResponseEntity<APIResponseDto<DeliveryAgentDto>> updateDeliveryAgent(Long id, DeliveryAgentDto dto);

	public ResponseEntity<APIResponseDto<DeliveryAgentDto>> updateStatus(Long id, boolean status);

	public ResponseEntity<APIResponseDto<DeliveryAgentDto>> viewById(Long id);

	public ResponseEntity<APIResponseDto<List<DeliveryAgentDto>>> viewByActiveStatus(boolean status);

	public ResponseEntity<APIResponseDto<Page<DeliveryAgentDto>>> getAllAgents(int page, int pageSize);

	public ResponseEntity<APIResponseDto<DeliveryAgentDto>> agentLogin(DeliveryAgentDto dto);

	public ResponseEntity<APIResponseDto<DeliveryAgentDto>> agentLogout(Long id);

	public ResponseEntity<APIResponseDto<DeliveryAgentDto>> emailVerify(DeliveryAgentDto dto);

	public ResponseEntity<APIResponseDto<DeliveryAgentDto>> sendEmailOtp(Long id);
	
	public ResponseEntity<APIResponseDto<DeliveryAgentDto>> changePassword(DeliveryAgentDto dto);
	
	public ResponseEntity<APIResponseDto<DeliveryAgentDto>> updateAvailability(DeliveryAgentDto dto);
	
	public ResponseEntity<APIResponseDto<DeliveryAgentDto>> updateLocation(DeliveryAgentDto dto);
	
	public ResponseEntity<APIResponseDto<DeliveryAgentDto>> respondtoRequest(DeliveryAgentDto dto);





}
