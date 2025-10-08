package com.example.Porter.DeliveryRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Porter.Constants.APIResponseDto;
import com.example.Porter.Constants.DeliveryRequestDto;
import com.example.Porter.Customer.CustomerModel;
import com.example.Porter.Customer.CustomerRepository;
import com.example.Porter.DeliveryRequest.DeliveryRequestModel.DeliveryStatus;
import com.example.Porter.Exception.AlreadyExistsException;
import com.example.Porter.Exception.FieldValidException;
import com.example.Porter.Exception.ResourceNotFoundException;
import com.example.Porter.Responsehandler.SuccessResponseHandler;
import com.example.Porter.Utils.MapperUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeliveryRequestServiceImpl implements DeliveryRequestService {

	private final CustomerRepository customerRepo;
	private final DeliveryRequestRepository deliveryRepo;

	@Override
	public ResponseEntity<APIResponseDto<DeliveryRequestDto>> addRequest(DeliveryRequestDto dto) {
		Optional<CustomerModel> customer = customerRepo.findById(dto.getCustomerId());
		if (customer.isEmpty()) {
			throw new ResourceNotFoundException("Customer not found");
		}

		CustomerModel details = customer.get();

		LocalDateTime scheduledTime = dto.getScheduledPickUpTime();
		boolean isDuplicate = deliveryRepo
				.existsByCustomerAndPickupAddressAndDropAddressAndParcelTypeAndParcelWeightAndScheduledPickUpTimeBetween(
						details, dto.getPickupAddress(), dto.getDropAddress(), dto.getParcelType(),
						dto.getParcelWeight(), scheduledTime.minusMinutes(5), scheduledTime.minusMinutes(5));
		if (!isDuplicate) {
			throw new AlreadyExistsException("Already placed delivery request, please wait for some time!");
		}

		LocalDateTime pickUpTime = scheduledTime != null ? dto.getScheduledPickUpTime() : LocalDateTime.now();

		DeliveryRequestModel delivery = DeliveryRequestModel.builder().actualFare(dto.getActualFare())
				.createdBy(dto.getCreatedBy()).customer(details)
				.deliveryStatus(DeliveryRequestModel.DeliveryStatus.REQUESTED).dropAddress(dto.getDropAddress())
				.dropContactName(dto.getDropContactName()).dropContactNo(dto.getDropContactNo())
				.dropLatitude(dto.getDropLatitude()).dropLongitude(dto.getDropLongitude()).isActive(true)
				.parcelType(dto.getParcelType()).parcelWeight(dto.getParcelWeight())
				.pickupAddress(dto.getPickupAddress()).pickupContactName(dto.getPickupContactName())
				.pickupContactNo(dto.getPickupContactNo()).pickupLatitude(dto.getPickupLatitude())
				.pickupLongitude(dto.getPickupLongitude()).scheduledPickUpTime(pickUpTime).build();
		deliveryRepo.save(delivery);
		DeliveryRequestDto response = MapperUtils.map(delivery, DeliveryRequestDto.class);
		return ResponseEntity.ok(SuccessResponseHandler.success(response, "Delivery request addedd successfully"));

	}

	@Override
	public ResponseEntity<APIResponseDto<DeliveryRequestDto>> updateRequest(Long id, DeliveryRequestDto dto) {
		Optional<DeliveryRequestModel> delivery = deliveryRepo.findById(id);
		if (delivery.isEmpty()) {
			throw new ResourceNotFoundException("Delivery request not found");
		}

		DeliveryRequestModel details = delivery.get();

		if (details.getDeliveryStatus() != DeliveryRequestModel.DeliveryStatus.REQUESTED
				&& details.getDeliveryStatus() != DeliveryRequestModel.DeliveryStatus.ASSIGNED) {
			throw new FieldValidException("Cannot edit the delivery request after pick_up the parcel");
		}

		details.setPickupAddress(dto.getPickupAddress());
		details.setPickupContactName(dto.getPickupContactName());
		details.setPickupContactNo(dto.getPickupContactNo());
		details.setPickupLatitude(dto.getPickupLatitude());
		details.setPickupLongitude(dto.getPickupLongitude());
		details.setDropAddress(dto.getDropAddress());
		details.setDropContactName(dto.getDropContactName());
		details.setDropContactNo(dto.getDropContactNo());
		details.setDropLatitude(dto.getDropLatitude());
		details.setDropLongitude(dto.getDropLongitude());
		details.setParcelType(dto.getParcelType());
		details.setParcelWeight(dto.getParcelWeight());

		LocalDateTime pickedUpTime = dto.getScheduledPickUpTime() != null ? dto.getScheduledPickUpTime()
				: LocalDateTime.now();
		details.setScheduledPickUpTime(pickedUpTime);
		details.setModifiedBy(dto.getModifiedBy());
		deliveryRepo.save(details);
		DeliveryRequestDto response = MapperUtils.map(delivery, DeliveryRequestDto.class);
		return ResponseEntity.ok(SuccessResponseHandler.success(response, "Delivery request updated successfully"));

	}

	@Override
	public ResponseEntity<APIResponseDto<DeliveryRequestDto>> updateStatus(Long id, String status) {
		Optional<DeliveryRequestModel> delivery = deliveryRepo.findById(id);
		if (delivery.isEmpty()) {
			throw new ResourceNotFoundException("Delivery request not found");
		}
		DeliveryRequestModel details = delivery.get();

		DeliveryStatus newStatus;
		try {
			newStatus = DeliveryStatus.valueOf(status.toUpperCase());
		} catch (Exception e) {
			throw new FieldValidException(e.getMessage());
		}

		DeliveryStatus oldStatus = details.getDeliveryStatus();
		if (!isValidation(oldStatus, newStatus)) {
			throw new FieldValidException("Invalid status");

		}

		details.setDeliveryStatus(newStatus);
		details.setModifiedBy("System");
		
		if(newStatus == DeliveryStatus.DELIVERED || newStatus == DeliveryStatus.CANCELLED) {
			details.setActive(false);
		}
		deliveryRepo.save(details);
		return ResponseEntity.ok(SuccessResponseHandler.success("Delivery request status updated successfully"));

	}
	
	private boolean isValidation(DeliveryStatus oldStatus,DeliveryStatus newStatus) {
		switch (oldStatus) {
		case REQUESTED : return newStatus == DeliveryStatus.ASSIGNED || newStatus == DeliveryStatus.CANCELLED;
		case ASSIGNED : return newStatus == DeliveryStatus.PICKED_UP || newStatus == DeliveryStatus.CANCELLED;
		case PICKED_UP : return newStatus == DeliveryStatus.IN_TRANSIT;
		case IN_TRANSIT : return newStatus == DeliveryStatus.DELIVERED ;
		default :return false;
		}
	}

	@Override
	public ResponseEntity<APIResponseDto<DeliveryRequestDto>> getById(Long id) {
		Optional<DeliveryRequestModel> delivery = deliveryRepo.findById(id);
		if (delivery.isEmpty()) {
			throw new ResourceNotFoundException("Delivery request not found");
		}

		DeliveryRequestDto response = MapperUtils.map(delivery.get(), DeliveryRequestDto.class);
		return ResponseEntity
				.ok(SuccessResponseHandler.success(response, "Delivery request fetched by ID successfully"));
	}

	@Override
	public ResponseEntity<APIResponseDto<List<DeliveryRequestDto>>> getByActiveStatus(boolean status) {
		List<DeliveryRequestModel> activeRequest = deliveryRepo.findByIsActive(status);
		if (activeRequest.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.success("No data found"));
		}

		List<DeliveryRequestDto> response = MapperUtils.mapList(activeRequest, DeliveryRequestDto.class);
		return ResponseEntity
				.ok(SuccessResponseHandler.success(response, "Delivery request fetched by Status Successfully"));

	}

	@Override
	public ResponseEntity<APIResponseDto<Page<DeliveryRequestDto>>> getAll(int page, int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<DeliveryRequestModel> content = deliveryRepo.findAllByOrderByDeliveryRequestIdDesc(pageable);

		if (content.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.success("No data found"));
		}

		Page<DeliveryRequestDto> response = MapperUtils.mapPage(content, DeliveryRequestDto.class);
		return ResponseEntity.ok(SuccessResponseHandler.success(response, "All Delivery request fetched successfully"));

	}

}
