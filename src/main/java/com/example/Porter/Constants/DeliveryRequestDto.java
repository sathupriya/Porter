package com.example.Porter.Constants;

import java.time.LocalDateTime;

import com.example.Porter.DeliveryRequest.DeliveryRequestModel.DeliveryStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeliveryRequestDto {

	private Long customerId;
	private Long deliveryRequestId;
	private String pickupAddress;
	private String pickupLatitude;
	private String pickupLongitude;
	private String pickupContactName;
	private String pickupContactNo;
	private String dropAddress;
	private String dropLatitude;
	private String dropLongitude;
	private String dropContactName;
	private String dropContactNo;
	private String parcelType;
	private Double parcelWeight;
	private DeliveryStatus deliveryStatus;
	private LocalDateTime scheduledPickUpTime;
	private LocalDateTime scheduledDropTime;
	private Double estimatedFare;
	private Double actualFare;
	private Double distanceInKm;
	private boolean isActive;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private String createdBy;
	private String modifiedBy;
}
