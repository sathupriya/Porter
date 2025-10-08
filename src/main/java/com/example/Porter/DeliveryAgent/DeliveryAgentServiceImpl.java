package com.example.Porter.DeliveryAgent;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Porter.Constants.APIResponseDto;
import com.example.Porter.Constants.CustomerDto;
import com.example.Porter.Constants.DeliveryAgentDto;
import com.example.Porter.Customer.CustomerModel;
import com.example.Porter.Email.EmailService;
import com.example.Porter.Exception.AlreadyExistsException;
import com.example.Porter.Exception.ResourceNotFoundException;
import com.example.Porter.Location.LocationModel;
import com.example.Porter.Location.LocationRepository;
import com.example.Porter.Responsehandler.SuccessResponseHandler;
import com.example.Porter.Security.JwtService;
import com.example.Porter.Security.SecurityConfig;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeliveryAgentServiceImpl implements DeliveryAgentService {

	private final DeliveryAgentRepository deliverAgentRepo;
	private final LocationRepository locationRepo;
	private final SecurityConfig security;
	private final EmailService service;
	private final JwtService jwtService;
	private final DeliveryAgentPasswordRepository passwordRepo;

	@Override
	public ResponseEntity<APIResponseDto<DeliveryAgentDto>> addDeliveryAgent(DeliveryAgentDto dto) {
		Optional<DeliveryAgentModel> mobile = deliverAgentRepo.findByMobileNumber(dto.getMobileNumber());
		Optional<DeliveryAgentModel> email = deliverAgentRepo.findByEmailAddress(dto.getEmailAddress());
		Optional<LocationModel> location = locationRepo.findById(dto.getLocationId());

		if (location.isEmpty()) {
			throw new ResourceNotFoundException("Location not found");
		}

		if (email.isPresent() && mobile.isPresent()) {
			throw new AlreadyExistsException(
					email.isPresent() ? "Email address already present, Please try with another email address"
							: "Mobile number already present, Please try with another mobile number");
		}

		String hashedPassword = security.passwordEncoder().encode(dto.getUserPassword());
		DeliveryAgentModel deliveryAgent = DeliveryAgentModel.builder().address(dto.getAddress())
				.createdBy(dto.getCreatedBy()).currentLattitude(dto.getCurrentLattitude())
				.currentLongitude(dto.getCurrentLongitude()).dateOfBirth(dto.getDateOfBirth())
				.deliveryAgentName(dto.getDeliveryAgentName()).emailAddress(dto.getEmailAddress())
				.location(location.get()).mobileNumber(dto.getMobileNumber()).hashedPassword(hashedPassword).build();

		DeliveryAgentModel deliveryRepo = deliverAgentRepo.save(deliveryAgent);

		DeliveryAgentPassword password = DeliveryAgentPassword.builder().currentPasswordStatus(true)
				.hashedPassword(hashedPassword).deliveryAgent(deliveryAgent).build();

		passwordRepo.save(password);

		DeliveryAgentDto saved = new DeliveryAgentDto();
		BeanUtils.copyProperties(deliveryRepo, saved);
		return ResponseEntity.ok(SuccessResponseHandler.success(saved, "Delivery agent addedd successfully"));

	}

	@Override
	public ResponseEntity<APIResponseDto<DeliveryAgentDto>> updateDeliveryAgent(Long id, DeliveryAgentDto dto) {
		Optional<DeliveryAgentModel> delivery = deliverAgentRepo.findById(id);
		if (delivery.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.success("Customer details not found"));
		}

		DeliveryAgentModel deliveryAgent = delivery.get();
		deliveryAgent.setAddress(dto.getAddress());
		deliveryAgent.setModifiedBy(dto.getModifiedBy());
		deliveryAgent.setDateOfBirth(dto.getDateOfBirth());
		deliveryAgent.setDeliveryAgentName(dto.getDeliveryAgentName());

		DeliveryAgentModel saved = deliverAgentRepo.save(deliveryAgent);

		DeliveryAgentDto response = new DeliveryAgentDto();
		BeanUtils.copyProperties(saved, response);

		return ResponseEntity
				.ok(SuccessResponseHandler.success(response, "Delivery agent details updated successfully"));
	}

	@Override
	public ResponseEntity<APIResponseDto<DeliveryAgentDto>> updateStatus(Long id, boolean status) {
		Optional<DeliveryAgentModel> delivery = deliverAgentRepo.findById(id);

		if (delivery.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.success("No data found"));
		}

		DeliveryAgentModel deliveryAgentDetails = delivery.get();
		deliveryAgentDetails.setActive(status);
		deliverAgentRepo.save(deliveryAgentDetails);
		return ResponseEntity
				.ok(SuccessResponseHandler.success(status == true ? "Delivery agent account activated successfully"
						: "Delivery agent account de-activated successfully"));
	}

	@Override
	public ResponseEntity<APIResponseDto<DeliveryAgentDto>> viewById(Long id) {
		Optional<DeliveryAgentModel> delivery = deliverAgentRepo.findById(id);

		if (delivery.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.success("No data found"));
		}

		DeliveryAgentModel deliveryAgentDetails = delivery.get();
		DeliveryAgentDto saved = new DeliveryAgentDto();
		BeanUtils.copyProperties(deliveryAgentDetails, saved);
		return ResponseEntity.ok(SuccessResponseHandler.success(saved, "Delivery Agent details fetched successfully"));
	}

	@Override
	public ResponseEntity<APIResponseDto<List<DeliveryAgentDto>>> viewByActiveStatus(boolean status) {
		List<DeliveryAgentModel> deliveryAgent = deliverAgentRepo.findByActiveStatus(status);
		if (deliveryAgent.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.noDataFound("No data found"));
		}

		List<DeliveryAgentDto> response = deliveryAgent.stream().map(model -> {
			DeliveryAgentDto saved = new DeliveryAgentDto();
			BeanUtils.copyProperties(model, saved);
			return saved;
		}).toList();

		return ResponseEntity.ok(SuccessResponseHandler.success(response, "Customer details fetched by status"));
	}

	@Override
	public ResponseEntity<APIResponseDto<Page<DeliveryAgentDto>>> getAllAgents(int page, int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<DeliveryAgentModel> content = deliverAgentRepo.findAllByOrderByDeliveryAgentIdDesc(pageable);

		if (content.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.noDataFound("No data found"));

		}

		Page<DeliveryAgentDto> response = content.map(model -> {
			DeliveryAgentDto saved = new DeliveryAgentDto();
			BeanUtils.copyProperties(model, saved);
			return saved;

		});
		return ResponseEntity.ok(SuccessResponseHandler.success(response, "All Delivery agent details fetched succesfully"));
	}

	@Override
	public ResponseEntity<APIResponseDto<DeliveryAgentDto>> agentLogin(DeliveryAgentDto dto) {
		return null;
	}

	@Override
	public ResponseEntity<APIResponseDto<DeliveryAgentDto>> agentLogout(Long id) {
		return null;
	}

	@Override
	public ResponseEntity<APIResponseDto<DeliveryAgentDto>> emailVerify(DeliveryAgentDto dto) {
		return null;
	}

	@Override
	public ResponseEntity<APIResponseDto<DeliveryAgentDto>> sendEmailOtp(Long id) {
		return null;
	}

	@Override
	public ResponseEntity<APIResponseDto<DeliveryAgentDto>> changePassword(DeliveryAgentDto dto) {
		return null;
	}

	@Override
	public ResponseEntity<APIResponseDto<DeliveryAgentDto>> updateAvailability(DeliveryAgentDto dto) {
		return null;
	}

	@Override
	public ResponseEntity<APIResponseDto<DeliveryAgentDto>> updateLocation(DeliveryAgentDto dto) {
		return null;
	}

	@Override
	public ResponseEntity<APIResponseDto<DeliveryAgentDto>> respondtoRequest(DeliveryAgentDto dto) {
		return null;
	}

}
