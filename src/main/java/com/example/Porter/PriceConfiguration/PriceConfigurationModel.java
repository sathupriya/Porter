package com.example.Porter.PriceConfiguration;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "price_configuration_table")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceConfigurationModel {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long priceConfigId;
	
	private Double baseFare;
	private Double ratePerKm;
	private Double ratePerKg;
	private Double cgstPer;
	private Double sgstPer;

	private Double igstPer;
	private boolean isTaxIncluded;
	private String zoneType;
	
	private boolean isActive;
	@CreationTimestamp
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime modifiedAt;
	private String createdBy;
	private String modifiedBy;
	

	
}
