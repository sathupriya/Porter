package com.example.Porter.DeliveryRequest;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.Porter.Customer.CustomerModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "delivery_request_table")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRequestModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	@Enumerated(EnumType.STRING)
	private DeliveryStatus deliveryStatus;
	private LocalDateTime scheduledPickUpTime;
	private LocalDateTime scheduledDropTime;
	private Double estimatedFare;
	private Double actualFare;
	private Double distanceInKm;
	private boolean isActive;
	@CreationTimestamp
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime modifiedAt;
	private String createdBy;
	private String modifiedBy;
	
	@ManyToOne(fetch=FetchType.LAZY,optional=false)
	@JoinColumn(name="customer_id",nullable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private CustomerModel customer;
	
	public enum DeliveryStatus{REQUESTED,ASSIGNED,PICKED_UP,IN_TRANSIT,DELIVERED,CANCELLED}

}
